package com.example.domain.repositories

import com.example.domain.models.WeatherData
import io.reactivex.Single

interface IWeatherRepository {
    fun getWeatherData(cityId: Int, unitSystem: String): Single<WeatherData>
    fun getDailyWeather(cityId: Int, days: Int, unitSystem: String): Single<List<WeatherData>>
    fun deleteOldWeatherDataFromEntity()
}