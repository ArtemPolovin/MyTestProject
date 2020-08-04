package com.example.domain.repositories

import com.example.domain.models.WeatherData
import io.reactivex.rxjava3.core.Single

interface WeatherDataRepository {

    fun getWeatherData(city: String, days: Int, degreeType: String): Single<WeatherData>
}