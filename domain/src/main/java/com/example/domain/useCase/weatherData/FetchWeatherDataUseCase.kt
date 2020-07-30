package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.WeatherDataRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchWeatherDataUseCase @Inject constructor(private val weatherDataRepository: WeatherDataRepository) {

    fun fetchWeatherData(city: String,days: Int,degreeType: String): Single<WeatherData> {
        return weatherDataRepository.getWeatherData(city,days,degreeType)
    }
}