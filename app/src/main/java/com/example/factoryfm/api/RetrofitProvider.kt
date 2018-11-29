package com.example.factoryfm.api

import com.example.factoryfm.utils.BASE_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object RetrofitProvider {
    fun get(apiKey: String): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor { chain ->

            val url = chain.request().url().newBuilder()
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("format", "json")
                .build()

            val request = chain
                .request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }.build()

        val gsonConverter = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonConverter))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}