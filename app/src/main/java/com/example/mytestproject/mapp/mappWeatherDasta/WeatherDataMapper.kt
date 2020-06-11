package com.example.mytestproject.mapp.mappWeatherDasta

import com.example.mytestproject.data.repository.responseWeatherData.WeatherDataApi
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData

class WeatherDataMapper {

    fun mapWeather(weatherDataApi: WeatherDataApi): WeatherData {
        return WeatherData(
            weatherDataApi.city_name,
            "${weatherDataApi.data[0].temp}",
             "https://www.weatherbit.io/static/img/icons/${
             weatherDataApi.data[0].weather.icon}.png"
        )
    }

}