package com.sam_chordas.android.stockhawk.service;

import com.sam_chordas.android.stockhawk.model.QuoteInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kssand on 22-Apr-16.
 */
public interface  QuoteService {
    //So these are the list available in our WEB API and the methods look straight forward


    @GET("public/yql")
    Call<QuoteInfo> getObjectWithNestedArraysAndObject(@Query("q") String q, @Query("diagnostics") String diagnostics,
                                                       @Query("env") String env, @Query("format") String format);
}
