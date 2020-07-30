package com.example.data.implementationRepo

import com.example.data.apiservice.WeatherDataApiService
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.models.WeatherData
import com.example.domain.repositories.WeatherDataRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherDataApiService : WeatherDataApiService,
    private val mapper : WeatherDataMapper
) : WeatherDataRepository {
    override fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherData> =
        weatherDataApiService.getWeatherData(city, days, degreeType)
            .map { mapper.mapWeather(it) }
}