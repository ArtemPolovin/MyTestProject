package com.example.data.mappers

import android.content.Context
import com.example.data.db.tables.TimezoneEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.utils.getCityModelByCityName

class TimezoneEntityMapper(private val context:Context) {

    fun fromApiToEntity(apiModel: CurrentWeatherApiModel): TimezoneEntity { // The method takes data from api and saves the data to SQLite TimezoneTable
        return TimezoneEntity(
            cityModel = getCityModelByCityName(context,apiModel.data[0].city_name),
            time_zone = apiModel.data[0].timezone
        )
    }
}