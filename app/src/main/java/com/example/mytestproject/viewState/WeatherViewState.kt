package com.example.mytestproject.viewState

import com.example.domain.models.WeatherData

sealed class WeatherViewState {

    object Loading : WeatherViewState()

    object Error : WeatherViewState()

    data class CurrentWeatherLoaded(
        val weatherData: WeatherData
    ) : WeatherViewState()

    data class DailyWeatherLoaded(
        val dailyWeatherList: List<WeatherData>
    ) : WeatherViewState()

    }