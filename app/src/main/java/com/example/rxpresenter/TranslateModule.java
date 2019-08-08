package com.example.rxpresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TranslateModule {

    @Provides
    @Singleton
    YandexTranslateService getTranslate(){
        return new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(Presenter.TRANSLATE_ENDPOINT)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(YandexTranslateService.class);
    }

}
