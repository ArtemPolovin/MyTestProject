package com.example.data.mappers

import com.example.data.db.tables.TimezoneEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.utils.CityConverter

class TimezoneEntityMapper(private val cityConverter: CityConverter) {

    fun fromApiToEntity(apiModel: CurrentWeatherApiModel, cityId: Int): TimezoneEntity { // The method takes data from api and saves the data to SQLite TimezoneTable
        return TimezoneEntity(
            cityModel =  cityConverter.getCityModelByCityId(cityId),
            time_zone = apiModel.data[0].timezone
        )
    }
}