package com.example.domain.repositories

import com.example.domain.models.WeatherData
import io.reactivex.Single

interface CurrentWeatherRepository {

    fun getWeatherData(city: String, degreeType: String): Single<WeatherData>
}