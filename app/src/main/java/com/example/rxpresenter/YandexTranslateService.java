package com.example.rxpresenter;

import com.example.rxpresenter.translate.Translate;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// https://translate.yandex.net/api/v1.5/tr.json/translate
// ?key=trnsl.1.1.20170331T163527Z.6d3042c2a9aad0e7.2ce36155bc4a0c5b432344b88b634c25287243c2
// &text=привет
// &lang=ru-fr
// &format=html

public interface YandexTranslateService {

    @GET("/api/v1.5/tr.json/translate")
    Call<Translate> translate(
            @Query("key") String key,
            @Query("lang") String lang,
            @Query("text") String text
    );

    @GET("/api/v1.5/tr.json/translate")
    Observable<Translate> translateR(
            @Query("key") String key,
            @Query("lang") String lang,
            @Query("text") String text
    );



}
