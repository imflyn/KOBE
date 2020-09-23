package com.flyn.kobe.net

import com.flyn.kobe.KobeApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .cache(Cache(KobeApplication.App.getContext()?.externalCacheDir!!, 10 * 1024 * 1024))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .addInterceptor(HttpLoggingInterceptor())

    fun <T> buildRetrofit(clazz: Class<T>): T {
        return Retrofit.Builder()
            .client(okHttp.build())
            .baseUrl("https://gank.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(clazz)
    }
}