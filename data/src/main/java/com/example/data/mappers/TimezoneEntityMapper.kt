package com.example.data.mappers

import com.example.data.db.tables.TimezoneTable
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel

class TimezoneEntityMapper {

    fun fromApiToEntity(apiModel: CurrentWeatherApiModel): TimezoneTable {
        return TimezoneTable(
            cityName = apiModel.data[0].city_name,
            time_zone = apiModel.data[0].timezone
        )
    }
}