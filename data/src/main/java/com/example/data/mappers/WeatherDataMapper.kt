package com.example.data.mappers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.ICON_URL
import com.example.data.utils.parsingDate
import com.example.domain.models.WeatherData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherDataMapper {

    fun mapWeather(currentWeatherApiModel: CurrentWeatherApiModel): WeatherData {
        return WeatherData(
            currentWeatherApiModel.data[0].city_name,
            "${currentWeatherApiModel.data[0].temp.toInt()}",
            "$ICON_URL${
            currentWeatherApiModel.data[0].weather.icon}.png",
            currentWeatherApiModel.data[0].datetime,
            currentWeatherApiModel.data[0].weather.description
        )
    }


    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToListOfWeather(dailyWeatherApi: DailyWeatherApi): List<WeatherData> {
        val list = mutableListOf<WeatherData>()

        for (element in dailyWeatherApi.data) {
            list.add(WeatherData(
                dailyWeatherApi.city_name,
                element.max_temp.toInt().toString(),
                "$ICON_URL${element.weather.icon}.png",
                parsingDate(element.datetime),
                element.weather.description
            ))
        }
        return list
    }

}