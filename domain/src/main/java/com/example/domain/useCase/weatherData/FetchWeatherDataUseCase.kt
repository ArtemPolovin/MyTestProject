package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.WeatherDataRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchWeatherDataUseCase (private val weatherDataRepository: WeatherDataRepository) {

    fun invoke(city: String, days: Int, degreeType: String): Single<WeatherData> {
        return weatherDataRepository.getWeatherData(city,days,degreeType)
    }
}