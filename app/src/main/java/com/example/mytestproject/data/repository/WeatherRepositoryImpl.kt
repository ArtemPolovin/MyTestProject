package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.core.Single

class WeatherRepositoryImpl : WeatherRepository {

    private val weatherDataMapper = WeatherDataMapper()

    override fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherData> =
        ApiService().getWeatherData(city, days, degreeType)
            .map {weatherDataMapper.mapWeather(it)}

}