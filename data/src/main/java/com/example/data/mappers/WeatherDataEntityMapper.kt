package com.example.data.mappers

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.data.db.entities.WeatherDataEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.ICON_URL
import com.example.data.utils.parsingDate
import com.example.domain.models.WeatherData
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataEntityMapper {
    @SuppressLint("SimpleDateFormat")
    fun fromApiToEntity(currentWeatherApi: CurrentWeatherApiModel): WeatherDataEntity {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        return WeatherDataEntity(
            currentWeatherApi.data[0].city_name,
            currentDate,
            currentWeatherApi.data[0].temp.toString(),
            "$ICON_URL${
            currentWeatherApi.data[0].weather.icon}.png",
            currentWeatherApi.data[0].weather.description
        )
    }

    fun fromApiToEntityList(dailyWeatherApi: DailyWeatherApi): List<WeatherDataEntity> {
        val list = mutableListOf<WeatherDataEntity>()

        for (element in dailyWeatherApi.data) {
            list.add(
                WeatherDataEntity(
                    dailyWeatherApi.city_name,
                    element.datetime,
                    element.max_temp.toInt().toString(),
                    "$ICON_URL${element.weather.icon}.png",
                    element.weather.description
                )
            )
        }
        return list
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromEntityToWeatherData(weatherDataEntity: WeatherDataEntity): WeatherData {
        return WeatherData(
            weatherDataEntity.cityName,
            weatherDataEntity.temperature,
            weatherDataEntity.icon,
            parsingDate(weatherDataEntity.date),
            weatherDataEntity.description
        )
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromEntityListToWeatherDataList(entityList: List<WeatherDataEntity>): List<WeatherData> {
        val list = mutableListOf<WeatherData>()

        for (element in entityList) {
            list.add(
                WeatherData(
                    element.cityName,
                    element.temperature,
                    element.icon,
                    parsingDate(element.date),
                    element.description
                )
            )
        }
        return list
    }
}
