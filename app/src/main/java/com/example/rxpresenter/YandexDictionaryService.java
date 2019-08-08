package com.example.rxpresenter;

import com.example.rxpresenter.dictionary.Dictionary;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://dictionary.yandex.net/api/v1/dicservice.json/lookup
// ?key=dict.1.1.20170402T202525Z.1ed8dd7c23205718.efda3a2c4bdf834b16909622280b20b0d60840a1
// &lang=fr-ru
// &text=chevalier


public interface YandexDictionaryService {

    @GET("/api/v1/dicservice.json/lookup")
    Call<Dictionary> lookup(
            @Query("key") String key,
            @Query("lang") String lang,
            @Query("text") String text
    );

    @GET("/api/v1/dicservice.json/lookup")
    Observable<Dictionary> lookupR(
            @Query("key") String key,
            @Query("lang") String lang,
            @Query("text") String text
    );

}
