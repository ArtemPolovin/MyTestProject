package com.example.data.mappers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.db.tables.WeatherDataTable
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.ICON_URL
import com.example.data.utils.getCityModelByCityName
import com.example.data.utils.getCurrentDateByTimezone
import com.example.data.utils.parsingDate
import com.example.domain.models.WeatherData
import kotlin.math.roundToInt

class WeatherDataEntityMapper(private val context: Context) {

    fun fromApiToEntity(currentWeatherApi: CurrentWeatherApiModel): WeatherDataTable { // The method takes data from api and saves the data to SQLite WeatherDataTable
        return WeatherDataTable(
                cityModel = getCityModelByCityName(context,currentWeatherApi.data[0].city_name),
                date = getCurrentDateByTimezone(currentWeatherApi.data[0].timezone),
                temperature = currentWeatherApi.data[0].temp.roundToInt().toString(),
                icon = "$ICON_URL${
                    currentWeatherApi.data[0].weather.icon}.png",
                description = currentWeatherApi.data[0].weather.description
        )
    }

    fun fromApiToEntityList(dailyWeatherApi: DailyWeatherApi): List<WeatherDataTable> { // The method takes list of days with weather data from api and saves the data to SQLite WeatherDataTable
        val list = mutableListOf<WeatherDataTable>()
        val cityModel = getCityModelByCityName(context,dailyWeatherApi.city_name)

        for (i in 1 until  dailyWeatherApi.data.size) { //The loop starts from the second element of the list because the first element (current day) is not included
            list.add(
                    WeatherDataTable(
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

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromEntityToWeatherData(weatherDataTable: WeatherDataTable): WeatherData { // The method takes weather data from SQLite table and save the data to WeatherData object
        return WeatherData(
                city_name = weatherDataTable.cityModel.city_name,
                temp = weatherDataTable.temperature,
                icon = weatherDataTable.icon,
                date = parsingDate(weatherDataTable.date),
                description = weatherDataTable.description
        )
    }

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    fun fromEntityListToWeatherDataList(tableList: List<WeatherDataTable>): List<WeatherData> { // The method takes list of days with weather data from SQLite table and save the data to list of WeatherData objects
        val list = mutableListOf<WeatherData>()

        for (element in tableList) {
            list.add(
                    WeatherData(
                            city_name = element.cityModel.city_name,
                            temp = element.temperature,
                            icon = element.icon,
                            date = parsingDate(element.date),
                            description = element.description
                    )
            )
        }
        return list
    }
}
