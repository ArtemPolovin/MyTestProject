package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.response.WeatherDataApi
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherDataApi>
}