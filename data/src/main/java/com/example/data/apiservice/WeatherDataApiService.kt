package com.example.data.apiservice

import com.example.data.modelsApi.weatherDataApiModel.WeatherDataApiModel
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherDataApiService {

    @GET("/v2.0/forecast/daily")
    fun getWeatherData(
        @Query("city") city: String,
        @Query("days") days: Int,
        @Query("units") degreeType: String
    ): Single<WeatherDataApiModel>


    companion object {
        operator fun invoke(): WeatherDataApiService {
            val key = "40a7956799be42f49bc8b6ac4bb8e432"
            val requestInterceptor = Interceptor{chain->
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

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherbit.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherDataApiService::class.java)
        }
    }
}