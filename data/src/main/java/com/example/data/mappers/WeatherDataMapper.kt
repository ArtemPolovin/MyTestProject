package com.example.data.mappers

import com.example.data.modelsApi.weatherDataApiModel.WeatherDataApiModel
import com.example.domain.models.WeatherData
import javax.inject.Inject

class WeatherDataMapper @Inject constructor(){

    fun mapWeather(weatherDataApiModel: WeatherDataApiModel): WeatherData {
        return WeatherData(
            weatherDataApiModel.city_name,
            "${weatherDataApiModel.data[0].temp}",
             "https://www.weatherbit.io/static/img/icons/${
             weatherDataApiModel.data[0].weather.icon}.png"
        )
    }

}