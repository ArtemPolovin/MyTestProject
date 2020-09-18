package com.example.domain.repositories

import com.example.domain.models.WeatherData
import io.reactivex.Single

interface IWeatherRepository {
    fun getWeatherData(cityId: Int, degreeType: String): Single<WeatherData>
    fun getDailyWeather(cityId: Int, days: Int, degreeType: String): Single<List<WeatherData>>
    fun deleteOldWeatherDataFromEntity()
}