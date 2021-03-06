package com.example.data.mappers

import com.example.data.db.tables.WeatherDataEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.*
import com.example.domain.models.WeatherData
import kotlin.math.roundToInt

class WeatherDataEntityMapper(
    private val cityConverter: CityConverter,
    private val settingsCache: SettingsCache
) {

    fun fromApiToEntity(currentWeatherApi: CurrentWeatherApiModel, cityId: Int): WeatherDataEntity { // The method takes data from api and saves the data to SQLite WeatherDataTable

        return WeatherDataEntity(
            cityModel = cityConverter.getCityModelByCityId(cityId),
            date = getCurrentDateByTimezone(currentWeatherApi.data[0].timezone),
            temperature = currentWeatherApi.data[0].temp.roundToInt().toString(),
            icon = "$ICON_URL${
                currentWeatherApi.data[0].weather.icon
            }.png",
            description = currentWeatherApi.data[0].weather.description
        )
    }

    fun fromApiToEntityList(dailyWeatherApi: DailyWeatherApi,cityId: Int): List<WeatherDataEntity> { // The method takes list of days with weather data from api and saves the data to SQLite WeatherDataTable
        val list = mutableListOf<WeatherDataEntity>()
        val cityModel = cityConverter.getCityModelByCityId(cityId)

        for (i in 1 until dailyWeatherApi.data.size) { //The loop starts from the second element of the list because the first element (current day) is not included
            list.add(
                WeatherDataEntity(
                    cityModel = cityModel,
                    date = dailyWeatherApi.data[i].datetime,
                    temperature = dailyWeatherApi.data[i].max_temp.roundToInt().toString(),
                    icon = "$ICON_URL${dailyWeatherApi.data[i].weather.icon}.png",
                    description = dailyWeatherApi.data[i].weather.description
                )
            )
        }
        return list
    }

    fun fromEntityToWeatherData(weatherDataEntity: WeatherDataEntity): WeatherData { // The method takes weather data from SQLite table and save the data to WeatherData object
        return WeatherData(
            city_name = weatherDataEntity.cityModel.city_name,
            temp = weatherDataEntity.temperature,
            icon = weatherDataEntity.icon,
            date = parsingDate(weatherDataEntity.date),
            description = weatherDataEntity.description,
            temperatureType = getUnitType()
        )
    }

    fun fromEntityListToWeatherDataList(entityList: List<WeatherDataEntity>): List<WeatherData> { // The method takes list of days with weather data from SQLite table and save the data to list of WeatherData objects

        return entityList.map {
            fromEntityToWeatherData(it)
        }
    }

    private fun getUnitType(): String {
        return when (settingsCache.getUnitSystem()) {
            METRIC -> CELSIUS_TYPE
            else -> FAHRENHEIT_TYPE
        }
    }

}
