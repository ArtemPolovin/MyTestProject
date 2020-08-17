package com.example.domain.repositories

import com.example.domain.models.WeatherData
import io.reactivex.Single

interface DailyWeatherRepository {
    fun getDailyWeather(cityId: Int, days: Int, degreeType: String): Single<List<WeatherData>>
}