package com.example.mytestproject.viewState

import com.example.domain.models.WeatherData

sealed class WeatherViewState {

    object Loading : WeatherViewState()

    object Error : WeatherViewState()

    data class WeatherLoaded(
        val weatherData: WeatherData
    ) : WeatherViewState()

    }