package com.example.data.mappers

import com.example.data.db.tables.WeatherDataEntity
import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.currentWeather.Data
import com.example.data.modelsApi.currentWeather.Weather
import com.example.data.modelsApi.multiDaysWeather.DailyData
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.CityConverter
import com.example.data.utils.ICON_URL
import com.example.domain.models.CityModel
import com.example.domain.models.WeatherData
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WeatherDataEntityMapperTest {

    private val cityConverter = mock(CityConverter::class.java)
    private val weatherDataEntityMapper = WeatherDataEntityMapper(cityConverter)

    @Test
    fun returnWeatherDataEntity() {
        // Given
        val cityId = 5128581
        val cityModel = CityModel(cityId, "New York City", "United States")
        val weather = Weather("800", "Clear sky", "c01d")
        val data = Data("New York City", 52.3, weather, "2020-09-23", "America/New_York")
        val dataList = listOf(data)
        val apiModel = CurrentWeatherApiModel(dataList)

        `when`(cityConverter.getCityModelByCityId(cityId)).thenReturn(cityModel)

        val weatherDataEntity =
            WeatherDataEntity(cityModel, "2020-11-11", "52", "${ICON_URL}c01d.png", "Clear sky") // The current date must be specified in the method parameters,
                                                                                                                                 // otherwise there will be an error

        // When
        val result = weatherDataEntityMapper.fromApiToEntity(apiModel, cityId)

        // Then
        assertEquals(weatherDataEntity,result )
    }

    @Test
    fun returnWeatherDataEntityList() {
        // Given
        val skippedCurrentWeather =
            com.example.data.modelsApi.multiDaysWeather.Weather(801, "Few clouds", "c02d")
        val weather1 = com.example.data.modelsApi.multiDaysWeather.Weather(801, "Few clouds", "c02d")
        val weather2 = com.example.data.modelsApi.multiDaysWeather.Weather(804, "Overcast clouds", "c04d")

        val skippedCurrentData = DailyData("2020-09-22", 92.8, skippedCurrentWeather)
        val data1 = DailyData("2020-09-23", 63.2, weather1)
        val data2 = DailyData("2020-09-24", 73.8, weather2)

        val dailyWeatherDataList = listOf(skippedCurrentData, data1, data2)
        val dailyWeatherApi = DailyWeatherApi("Moscow", dailyWeatherDataList)

        val cityId = 5128581
        val cityModel = CityModel(cityId, "New York City", "United States")

        `when`(cityConverter.getCityModelByCityId(cityId)).thenReturn(cityModel)

        val weatherDataEntityList = listOf(
            WeatherDataEntity(cityModel, "2020-09-23", "63", "${ICON_URL}c02d.png", "Few clouds"),
            WeatherDataEntity(
                cityModel,
                "2020-09-24",
                "74",
                "${ICON_URL}c04d.png",
                "Overcast clouds"
            )
        )

        // When
        val result =  weatherDataEntityMapper.fromApiToEntityList(dailyWeatherApi, cityId)

        // Then
        assertEquals(weatherDataEntityList, result)
    }

    @Test
    fun returnWeatherData() {
        // Given
        val cityModel = CityModel(4343, "Milan", "Italy")
        val weatherDataEntity =
            WeatherDataEntity(cityModel, "2020-09-23", "82", "iconUrl", "Few clouds")

        val weatherData = WeatherData("Milan", "82", "iconUrl", "Wednesday, 23", "Few clouds")

        // When
        val result = weatherDataEntityMapper.fromEntityToWeatherData(weatherDataEntity)

        // Then
        assertEquals(weatherData,result)
    }

    @Test
    fun returnWeatherDataList() {
        // Given
        val moscowCityModel = CityModel(2323, "Moscow", "Russia")
        val kievCityModel = CityModel(1233, "Kiev", "Ukraine")

        val weatherDataEntityList = listOf(
            WeatherDataEntity(moscowCityModel, "2020-09-23", "82", "iconUrl1", "Few clouds"),
            WeatherDataEntity(kievCityModel, "2020-09-23", "74", "iconUrl2", "Overcast clouds")
        )

        val weatherDataList = listOf(
            WeatherData("Moscow", "82", "iconUrl1", "Wednesday, 23", "Few clouds"),
            WeatherData("Kiev", "74", "iconUrl2", "Wednesday, 23", "Overcast clouds")
        )

        // When
        val result =  weatherDataEntityMapper.fromEntityListToWeatherDataList(weatherDataEntityList)

        // Then
        assertEquals(weatherDataList,result)
    }

}