package com.example.data.mappers

import com.example.data.modelsApi.weatherDataApiModel.WeatherDataApiModel
import com.example.data.utils.ICON_URL
import com.example.domain.models.WeatherData

class WeatherDataMapper {

    fun mapWeather(weatherDataApiModel: WeatherDataApiModel): WeatherData {
        return WeatherData(
            weatherDataApiModel.city_name,
            "${weatherDataApiModel.data[0].temp}",
            "$ICON_URL${
            weatherDataApiModel.data[0].weather.icon}.png"
        )
    }

}