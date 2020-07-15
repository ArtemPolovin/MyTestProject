package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.RetrofitBuilder
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.core.Single

object WeatherRepository {
    private val mapper = WeatherDataMapper()

    fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherData> =
        RetrofitBuilder.apiService.getWeatherData(city, days, degreeType)
            .map { mapper.mapWeather(it) }
}