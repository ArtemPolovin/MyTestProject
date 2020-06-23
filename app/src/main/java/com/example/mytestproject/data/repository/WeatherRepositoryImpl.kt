package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherRepositoryImpl(
   private val weatherDataMapper: WeatherDataMapper,
   private val apiService: ApiService
) : WeatherRepository {

    override fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherData> =
        apiService.getWeatherData(city, days, degreeType)
            .map {weatherDataMapper.mapWeather(it)}

}