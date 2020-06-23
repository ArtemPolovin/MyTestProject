package com.example.mytestproject.ui.viewState

import com.example.mytestproject.ui.models.weatherDataModel.WeatherData

sealed class WeatherViewState {

    object Loading : WeatherViewState()

    object Error : WeatherViewState()

    data class WeatherLoaded(
        val weatherData: WeatherData
    ) : WeatherViewState()

    }