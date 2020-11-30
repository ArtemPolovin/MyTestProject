package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.IWeatherRepository
import io.reactivex.Single

class FetchDailyWeatherUseCase (private val weatherRepository: IWeatherRepository){
    operator fun invoke(cityId: Int, days: Int): Single<List<WeatherData>> {
        return weatherRepository.getDailyWeather(cityId,days)
    }
}
