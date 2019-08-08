package com.example.rxpresenter;


import android.content.Context;

import com.example.rxpresenter.dictionary.Dictionary;

import javax.inject.Inject;

public class Repository {


    @Inject
    public YandexDictionaryService dictionaryService;

    @Inject
    public YandexTranslateService translateService;


    public YandexDictionaryService getDictionaryService() {
        return dictionaryService;
    }

    public YandexTranslateService getTranslateService() {

        return translateService;
    }

  public  Repository() {
      App.getAppComponent().inject(this);
    }



}
