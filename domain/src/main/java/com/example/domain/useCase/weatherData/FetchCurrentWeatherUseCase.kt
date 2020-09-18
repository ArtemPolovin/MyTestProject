package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.IWeatherRepository
import io.reactivex.Single

class FetchCurrentWeatherUseCase (private val weatherRepository: IWeatherRepository) {

   operator fun invoke(cityId: Int, degreeType: String): Single<WeatherData> {
        return weatherRepository.getWeatherData(cityId,degreeType)
    }
}