package com.example.domain.useCase.weatherData

import com.example.domain.models.WeatherData
import com.example.domain.repositories.DailyWeatherRepository
import io.reactivex.Single

class FetchDailyWeatherUseCase (private val dailyWeatherRepository: DailyWeatherRepository){
    operator fun invoke(city: String, days: Int, degreeType: String): Single<List<WeatherData>> {
        return dailyWeatherRepository.getDailyWeather(city,days,degreeType)
    }
}
