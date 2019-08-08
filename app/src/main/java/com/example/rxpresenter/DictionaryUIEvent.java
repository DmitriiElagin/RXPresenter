package com.example.rxpresenter;

public class DictionaryUIEvent implements UIEvent {

    private String lang;
    private String dictionary;


    public DictionaryUIEvent(String lang, String dictionary) {
        this.lang = lang;
        this.dictionary = dictionary;
    }

    public String getLang() {
        return lang;
    }

    public String getDictionary() {
        return dictionary;
    }
}
