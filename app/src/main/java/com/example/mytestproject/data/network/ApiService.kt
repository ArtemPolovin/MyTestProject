package com.example.mytestproject.data.network

import com.example.mytestproject.data.network.response.ResponseTomorrowWeatherData
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2.0/forecast/daily")
    fun getTomorrowWeather(
        @Query("key") key: String,
        @Query("city") city: String,
        @Query("days") days: Int,
        @Query("units") degreeType: String
    ):Single<ResponseTomorrowWeatherData>


    companion object {
        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://api.weatherbit.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}