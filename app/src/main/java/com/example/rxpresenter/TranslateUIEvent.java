package com.example.rxpresenter;

public class TranslateUIEvent implements UIEvent {
    private String lang;
    private String translate;

    public TranslateUIEvent(String lang, String translate) {
        this.lang = lang;
        this.translate = translate;
    }

    public String getLang() {
        return lang;
    }

    public String getTranslate() {
        return translate;
    }
}
