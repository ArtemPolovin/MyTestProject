package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.CurrentWeatherRepository
import io.reactivex.Single

class FetchCurrentWeatherUseCase (private val currentWeatherRepository: CurrentWeatherRepository) {

   operator fun invoke(city: String, degreeType: String): Single<WeatherData> {
        return currentWeatherRepository.getWeatherData(city,degreeType)
    }
}