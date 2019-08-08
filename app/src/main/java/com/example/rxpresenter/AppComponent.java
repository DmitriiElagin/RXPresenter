package com.example.rxpresenter;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MainModule.class,TranslateModule.class,DictionaryModule.class})
public interface AppComponent {

    void inject(@NonNull MainActivity mainActivity);
    void inject(@NonNull Repository repository);
    void inject(@NonNull Presenter presenter);
}
