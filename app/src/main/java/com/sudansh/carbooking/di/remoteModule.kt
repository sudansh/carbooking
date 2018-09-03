package com.sudansh.carbooking.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.sudansh.carbooking.data.LiveDataCallAdapterFactory
import com.sudansh.carbooking.repository.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteModule = applicationContext {
    bean { createOkHttpClient() }
    bean { createWebService<ApiService>(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        connectTimeout(60L, TimeUnit.SECONDS)
        readTimeout(60L, TimeUnit.SECONDS)
        addNetworkInterceptor(StethoInterceptor())
    }.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T = Retrofit.Builder()
        .baseUrl("https://challenge.smove.sg/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()
        .create(T::class.java)