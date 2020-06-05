package com.example.mytestproject.ui.models.weatherDataModel

import com.example.mytestproject.data.repository.responseWeatherData.Data

data class WeatherData(val city_name: String, val data: List<Data>)