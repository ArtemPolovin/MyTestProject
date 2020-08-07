package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.WeatherDataRepository
import io.reactivex.Single

class FetchWeatherDataUseCase (private val weatherDataRepository: WeatherDataRepository) {

    fun invoke(city: String, days: Int, degreeType: String): Single<WeatherData> {
        return weatherDataRepository.getWeatherData(city,days,degreeType)
    }
}