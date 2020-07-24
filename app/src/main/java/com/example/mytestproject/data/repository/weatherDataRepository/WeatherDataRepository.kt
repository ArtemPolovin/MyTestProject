package com.example.mytestproject.data.repository.weatherDataRepository

import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.core.Single

interface WeatherDataRepository {

    fun getWeatherData(city: String, days: Int, degreeType: String): Single<WeatherData>
}