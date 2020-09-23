package com.example.data.mappers

import com.example.data.db.tables.TimezoneEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.currentWeather.Data
import com.example.data.modelsApi.currentWeather.Weather
import com.example.data.utils.CityConverter
import com.example.domain.models.CityModel
import org.mockito.Mockito.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TimezoneEntityMapperTest {

    @Test
    fun returnTimezoneEntity() {
        val cityId = 5128581
        val cityModel = CityModel(cityId,"New York City","United States")

        val cityConverter = mock(CityConverter::class.java)
        `when`(cityConverter.getCityModelByCityId(cityId)).thenReturn(cityModel)

        val timezoneEntityMapper = TimezoneEntityMapper(cityConverter)
        val weather = Weather("800", "Clear sky", "iconURL")
        val data = Data("New York City", 52.3, weather, "2020-09-22", "America/New_York")
        val dataList = listOf(data)
        val apiModel = CurrentWeatherApiModel(dataList)

        assertEquals( TimezoneEntity(cityModel, "America/New_York"),
            timezoneEntityMapper.fromApiToEntity(apiModel, cityId)
        )


    }
}