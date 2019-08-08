package com.example.rxpresenter;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Test {

    public static void main(String[] args) {
        Observable<String> my=Observable.just("one", "two", "tree");

        my.
//                subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
        compose(t);
    }

    public static ObservableTransformer<String, String> t= upstream -> upstream
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
}
