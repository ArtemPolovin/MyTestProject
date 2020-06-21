package com.example.mytestproject.data.repository.weatherRepository

import com.example.mytestproject.data.repository.responseWeatherData.WeatherDataApi
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.core.Single

interface WeatherRepository {
    fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherData>
}