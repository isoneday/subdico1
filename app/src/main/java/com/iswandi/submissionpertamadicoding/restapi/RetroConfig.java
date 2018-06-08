package com.iswandi.submissionpertamadicoding.restapi;

import com.iswandi.submissionpertamadicoding.helper.MyConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by iswandisaputra on 3/1/18.
 */

public class RetroConfig {
    private static Retrofit getRetrofit(){
        //insialisasi retrofit 2
        Retrofit r = new Retrofit.Builder()
                .baseUrl(MyConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return r;
    }
    public static RetroApi getInstaceRetrofit(){
        return getRetrofit().create(RetroApi.class);
    }
}
