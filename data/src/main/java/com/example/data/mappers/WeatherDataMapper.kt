package com.example.data.mappers

import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.*
import com.example.domain.models.WeatherData
import kotlin.math.roundToInt

class WeatherDataMapper (private val settingsCache: SettingsCache){


    fun mapApiToWeatherDataModel(currentWeatherApiModel: CurrentWeatherApiModel): WeatherData { // The method takes current weather data from api and save it to WeatherData object

        return WeatherData(
            city_name = currentWeatherApiModel.data[0].city_name,
            temp = "${currentWeatherApiModel.data[0].temp.roundToInt()}",
            icon = "$ICON_URL${
            currentWeatherApiModel.data[0].weather.icon}.png",
            date = currentWeatherApiModel.data[0].datetime,
            description = currentWeatherApiModel.data[0].weather.description,
            temperatureType = getUnitType()
        )
    }

    fun mapToListOfDailyWeatherData(dailyWeatherApi: DailyWeatherApi): List<WeatherData> { // The method takes list of days with weather data from api and save it to list of WeatherData objects
        val list = mutableListOf<WeatherData>()

        for (i in 1 until  dailyWeatherApi.data.size) {  // The loop starts from the second element of the list because the first element (current day) is not included
            list.add(
                WeatherData(
                    city_name = dailyWeatherApi.city_name,
                    temp = dailyWeatherApi.data[i].max_temp.roundToInt().toString(),
                    icon = "$ICON_URL${dailyWeatherApi.data[i].weather.icon}.png",
                    date = parsingDate(dailyWeatherApi.data[i].datetime),
                    description = dailyWeatherApi.data[i].weather.description,
                    temperatureType = getUnitType()
                )
            )
        }
        return list
    }

    private fun getUnitType(): String {
        return when (settingsCache.getUnitSystem()) {
            METRIC -> CELSIUS_TYPE
            else -> FAHRENHEIT_TYPE
        }
    }

}