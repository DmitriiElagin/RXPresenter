package com.example.rxpresenter;


import com.example.rxpresenter.dictionary.Dictionary;
import com.example.rxpresenter.translate.Translate;



public class UIModel {
    private UIModelState state;
    private Dictionary dictionary;
    private Translate translate;
    private Throwable error;

    private UIModel(Throwable t) {
        this.error =t;
        state=UIModelState.ERROR;
    }

    private UIModel(Dictionary d) {
        this.dictionary =d;
        state=UIModelState.DICTIONARY_SUCCESS;
    }

    private UIModel(Translate t) {
        this.translate =t;
        state=UIModelState.TRANSLATE_SUCCESS;
    }

    private UIModel(UIModelState state){
        this.state=state;
    }

    public UIModelState getState() {
        return state;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Translate getTranslate() {
        return translate;
    }

    public Throwable getError() {
        return error;
    }

    public static UIModel error(Throwable t){
        return  new UIModel(t);

        }

    public static UIModel success(Dictionary d){
        return  new UIModel(d);
    }

    public static UIModel success(Translate t){
        return  new UIModel(t);
    }

    public static UIModel idle (){
        return  new UIModel(UIModelState.IDLE);
    }

    public static UIModel startTranslate(){
        return  new UIModel(UIModelState.TRANSLATE_IN_FLIGHT);
    }

    public static UIModel startDictionary(){
        return  new UIModel(UIModelState.DICTIOARY_IN_FLIGHT);
    }












    public enum UIModelState {

        IDLE, DICTIOARY_IN_FLIGHT,
        TRANSLATE_IN_FLIGHT, DICTIONARY_SUCCESS,
        TRANSLATE_SUCCESS, ERROR

    }
}
