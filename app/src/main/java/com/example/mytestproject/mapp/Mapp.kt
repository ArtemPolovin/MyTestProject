package com.example.mytestproject.mapp

import com.example.mytestproject.data.repository.responseWeatherData.WeatherDataApi
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData

fun WeatherDataApi.mapToWeatherData(): WeatherData {
    return WeatherData(city_name = this.city_name, data = this.data)
}