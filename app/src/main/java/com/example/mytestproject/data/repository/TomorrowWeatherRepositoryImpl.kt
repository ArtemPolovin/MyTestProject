package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.data.network.response.WeatherDataApi
import io.reactivex.rxjava3.core.Single

class TomorrowWeatherRepositoryImpl:  TomorrowWeatherRepository {
    override fun getTomorrowWeatherData(
        key: String,
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherDataApi> =
        ApiService().getTomorrowWeather(key, city, days, degreeType)

}