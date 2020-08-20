package com.example.data.mappers

import com.example.data.db.tables.WeatherDataEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.ICON_URL
import com.example.data.utils.getCurrentDateByTimezone
import com.example.data.utils.parsingDate
import com.example.domain.models.WeatherData
import kotlin.math.roundToInt

class WeatherDataEntityMapper {
    fun fromApiToEntity(currentWeatherApi: CurrentWeatherApiModel): WeatherDataEntity {

        return WeatherDataEntity(
            cityName = currentWeatherApi.data[0].city_name,
            date = getCurrentDateByTimezone(currentWeatherApi.data[0].timezone),
            temperature = currentWeatherApi.data[0].temp.roundToInt().toString(),
            icon = "$ICON_URL${
                currentWeatherApi.data[0].weather.icon
            }.png",
            description = currentWeatherApi.data[0].weather.description
        )
    }

    fun fromApiToEntityList(dailyWeatherApi: DailyWeatherApi): List<WeatherDataEntity> {
        val list = mutableListOf<WeatherDataEntity>()

        for (i in 1 until dailyWeatherApi.data.size) { //The loop starts from the second element of the list because the first element (current day) is not included
            list.add(
                WeatherDataEntity(
                    cityName = dailyWeatherApi.city_name,
                    date = dailyWeatherApi.data[i].datetime,
                    temperature = dailyWeatherApi.data[i].max_temp.roundToInt().toString(),
                    icon = "$ICON_URL${dailyWeatherApi.data[i].weather.icon}.png",
                    description = dailyWeatherApi.data[i].weather.description
                )
            )
        }
        return list
    }

    fun fromEntityToWeatherData(weatherDataEntity: WeatherDataEntity): WeatherData {
        return WeatherData(
            city_name = weatherDataEntity.cityName,
            temp = weatherDataEntity.temperature,
            icon = weatherDataEntity.icon,
            date = parsingDate(weatherDataEntity.date),
            description = weatherDataEntity.description
        )
    }

    fun fromEntityListToWeatherDataList(entityList: List<WeatherDataEntity>): List<WeatherData> {

        return entityList.map {
            WeatherData(
                city_name = it.cityName,
                temp = it.temperature,
                icon = it.icon,
                date = parsingDate(it.date),
                description = it.description
            )
        }
    }
}
