package com.example.rxpresenter;



import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Presenter {

    public static final String TRANSLATE_API_KEY = "trnsl.1.1.20170331T163527Z.6d3042c2a9aad0e7.2ce36155bc4a0c5b432344b88b634c25287243c2";
    public static final String TRANSLATE_ENDPOINT = "https://translate.yandex.net";

    public static final String DICTIONARY_API_KEY = "dict.1.1.20170402T202525Z.1ed8dd7c23205718.efda3a2c4bdf834b16909622280b20b0d60840a1";
    public static final String DICTIONARY_ENDPOINT = "https://dictionary.yandex.net";


    Presenter () {
        App.getAppComponent().inject(this);
        translate=repository.getTranslateService();
        dictionary=repository.getDictionaryService();
    }

    @Inject
    public Repository repository;

    private static YandexTranslateService translate;

//            new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .baseUrl(TRANSLATE_ENDPOINT)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(YandexTranslateService.class);


    private static YandexDictionaryService dictionary;
//            new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .baseUrl(DICTIONARY_ENDPOINT)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                    .create(YandexDictionaryService.class);
    Observable<UIEvent> events=Observable.create(presenterObservableOnSubscribe);


   private static ObservableTransformer<TranslateUIEvent,UIModel>
        translateUIEventUIModelObservableTransformer =
            upstream -> upstream
            .switchMap(e->translate.translateR(TRANSLATE_API_KEY,e.getLang(),e.getTranslate())

                    .map(UIModel::success)
                    .onErrorReturn(UIModel::error)
                    .startWith(UIModel.startTranslate())
                    .subscribeOn(Schedulers.io())
            );



 private static    ObservableTransformer<DictionaryUIEvent,UIModel>
            dictionaryUIEventUIModelObservableTransformer=
            upstream -> upstream
            .switchMap(e->dictionary.lookupR(DICTIONARY_API_KEY,e.getLang(),e.getDictionary())

                    .map(UIModel::success)
                    .onErrorReturn(UIModel::error)
                    .startWith(UIModel.startDictionary())
                    .subscribeOn(Schedulers.io())
            );


    private static ObservableTransformer<UIEvent,UIModel> transformer =
            upstream -> upstream
            .publish(
                    event->Observable.merge(
                            event.ofType(DictionaryUIEvent.class).compose(dictionaryUIEventUIModelObservableTransformer),
                            event.ofType(TranslateUIEvent.class).compose(translateUIEventUIModelObservableTransformer)
                    )
            )
            . observeOn(AndroidSchedulers.mainThread())
                    .replay(1)
                    .autoConnect(1);

    Observable<UIModel> models=events.compose(transformer);

//    Observable<UIModel> models=events
//            .ofType(TranslateUIEvent.class)//берет события только определенного класса
//            .doOnNext(event-> Log.d("happy","presenter.translate "+event.getTranslate()))
//            .switchMap(uiEvent -> translate.translateR(TRANSLATE_API_KEY,
//                    uiEvent.getLang(),
//                    uiEvent.getTranslate()
//                    )
////                    .delay(3, TimeUnit.SECONDS)
//                    .map(UIModel::success)
//                    .onErrorReturn(UIModel::error)
//                    .startWith(UIModel.startTranslate())//стартует с указанного элемента
//                    .subscribeOn(Schedulers.io())
//
//            )
//            .observeOn(AndroidSchedulers.mainThread())
//            .replay(1)
//            .autoConnect(1);


    static class MyObservableOnSubscribe implements ObservableOnSubscribe<UIEvent> {
        ObservableEmitter<UIEvent> emitter;
        @Override
        public void subscribe(ObservableEmitter<UIEvent> emitter) throws Exception {

            this.emitter=emitter;
        }

        public void onEvent(UIEvent event) {
            if(!emitter.isDisposed()) {
                emitter.onNext(event);
            }
        }
    }


    private static MyObservableOnSubscribe presenterObservableOnSubscribe =
            new MyObservableOnSubscribe();


    public Observable<UIModel> getObservable(){

        return models;
    }




    public void onEvent(UIEvent event) {

        presenterObservableOnSubscribe.onEvent(event);
    }
}
