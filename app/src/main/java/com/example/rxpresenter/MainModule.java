package com.example.rxpresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    @Singleton
    Repository getRepository() {
        return  new Repository();
    }

    @Provides
    @Singleton
    Presenter getPresenter(){
        return new Presenter();
    }
}
