package com.example.domain.repositories

import com.example.domain.models.WeatherData
import io.reactivex.Single

interface CurrentWeatherRepository {

    fun getWeatherData(cityId: Int, degreeType: String): Single<WeatherData>
}