package com.example.mytestproject.data.network

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://api.weatherbit.io"

    val apiService: ApiService by lazy {
        val key = "40a7956799be42f49bc8b6ac4bb8e432"
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url() // HttpUrl
                .newBuilder() // HttpUrl.Builder
                .addQueryParameter("key", key) // HttpUrl.Builder
                .build()
            val request = chain.request()
                .newBuilder() // Request.Builder
                .url(url) // Request.Builder
                .build() // Request
            return@Interceptor chain.proceed(request) // Response
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor) // OkHttpClient.Builder()
            .build()

        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


}

