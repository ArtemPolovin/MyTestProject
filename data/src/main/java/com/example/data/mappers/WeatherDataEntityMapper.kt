package com.example.data.mappers

import android.annotation.SuppressLint
import com.example.data.db.entities.WeatherDataEntity
import com.example.data.modelsApi.weatherDataApiModel.WeatherDataApiModel
import com.example.data.utils.ICON_URL
import com.example.domain.models.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataEntityMapper {
    @SuppressLint("SimpleDateFormat")
    fun fromApiToEntity(weatherDataApi: WeatherDataApiModel): WeatherDataEntity {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        return WeatherDataEntity(
            weatherDataApi.city_name,
            currentDate,
            weatherDataApi.data[0].temp.toString(),
            "$ICON_URL${
            weatherDataApi.data[0].weather.icon}.png"
        )
    }

    fun fromEntityToWeatherData(weatherDataEntity: WeatherDataEntity): WeatherData {
        return WeatherData(
            weatherDataEntity.cityName,
            weatherDataEntity.temperature,
            weatherDataEntity.icon
        )
    }
}