package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.data.network.response.WeatherDataApi
import io.reactivex.rxjava3.core.Single

class WeatherRepositoryImpl:  WeatherRepository {
    override fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherDataApi> =
        ApiService().getWeatherData(city, days, degreeType)

}