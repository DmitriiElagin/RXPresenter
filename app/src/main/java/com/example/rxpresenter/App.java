package com.example.rxpresenter;

import android.app.Application;

public class App extends Application {


    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();


        component=DaggerAppComponent.builder()
                .mainModule(new MainModule())
                .translateModule(new TranslateModule())
                .dictionaryModule(new DictionaryModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return component;
    }
}
