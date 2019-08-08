package com.example.rxpresenter;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rxpresenter.dictionary.Dictionary;
import com.example.rxpresenter.translate.Translate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    private static Gson gson = new GsonBuilder().create();

    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public Presenter presenter;

    private Observable<TranslateUIEvent> translateUIEventObservable;
    private Observable<DictionaryUIEvent> dictionaryUIEventObservable;
    private Observable<UIEvent> uiEventObservable;


    private EditText translateSource;
    private EditText dictionarySource;
    private TextView resultOfTranslate;
    private TextView resultOfDictionary;
    private Button startTranslate;
    private Button startDictionary;
    private ProgressBar progressBar;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        setContentView(R.layout.activity_main);
        translateSource = findViewById(R.id.sourceOfTranslate);
        dictionarySource = findViewById(R.id.sourceOfDictionary);
        resultOfTranslate = findViewById(R.id.resultOfTranslate);
        resultOfDictionary = findViewById(R.id.resultOfDictionary);
        progressBar = findViewById(R.id.progress);
        root = findViewById(R.id.root);

        startDictionary = findViewById(R.id.startDictionary);
        startTranslate = findViewById(R.id.startTranslate);

        translateUIEventObservable= RxView.
                clicks(startTranslate)
                .map(ignored->translateSource.getText())
                .map(CharSequence::toString)
                .filter(string->string.length()>3)
                .map(string->new TranslateUIEvent("ru-en",string));


        dictionaryUIEventObservable=RxView.
                clicks(startDictionary)
                .map(ignored->dictionarySource.getText())
                .map(CharSequence::toString)
                .filter(string->string.length()>3)
                .map(string->new DictionaryUIEvent("ru-ru", string));

        uiEventObservable= Observable.merge
                (dictionaryUIEventObservable,
                        translateUIEventObservable);


        disposables.add(
                uiEventObservable.subscribe(
                        presenter::onEvent
                )
        );

        Observable<UIModel>uiModelObservable=presenter.getObservable();

        disposables.add(
                uiModelObservable.subscribe(
                        model->
                        {
                            UIModel.UIModelState state=model.getState();

                            if(state==UIModel.UIModelState.DICTIOARY_IN_FLIGHT||
                                    state==UIModel.UIModelState.TRANSLATE_IN_FLIGHT){

                                progressBar.setVisibility(View.VISIBLE);
                            }

//                            else{
//                                progressBar.setVisibility(View.INVISIBLE);
//                            }


                            if(state== UIModel.UIModelState.ERROR) {

                                Throwable t= model.getError();
                                Snackbar.make(root, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            else if(state== UIModel.UIModelState.DICTIONARY_SUCCESS)
                            {
                                Dictionary d=model.getDictionary();
                                String result=gson.toJson(d);
                                resultOfDictionary.setText(result);
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                            else if(state== UIModel.UIModelState.TRANSLATE_SUCCESS){
                                Translate t=model.getTranslate();
                                String result=gson.toJson(t);
                                resultOfTranslate.setText(result);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposables != null && !disposables.isDisposed()) {
            disposables.clear();
        }
    }
    }

