package com.example.data.mappers

import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.currentWeather.Data
import com.example.data.modelsApi.currentWeather.Weather
import com.example.data.modelsApi.multiDaysWeather.DailyData
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.ICON_URL
import com.example.domain.models.WeatherData
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WeatherDailyDataMapperTest {

    private val weatherDataMapper = WeatherDataMapper()

    @Test
    fun returnWeatherDataObject() {
        // Given
        val weather = Weather("800", "Clear sky", "c01d")
        val data = Data("New York City", 52.3, weather, "2020-09-22", "America/New_York")
        val dataList = listOf(data)
        val apiModel = CurrentWeatherApiModel(dataList)

        val weatherData =
            WeatherData("New York City", "52", "${ICON_URL}c01d.png", "2020-09-22", "Clear sky")

        // When
        val result = weatherDataMapper.mapWeather(apiModel)

        // Then
        assertEquals(weatherData,result )
    }

    @Test
    fun returnListWeatherData() {
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

        val weatherDataList = listOf(
            WeatherData("Moscow", "63", "${ICON_URL}c02d.png", "Wednesday, 23", "Few clouds"),
            WeatherData("Moscow", "74", "${ICON_URL}c04d.png", "Thursday, 24", "Overcast clouds")
        )

        // When
        val result = weatherDataMapper.mapToListOfWeather(dailyWeatherApi)

        // Then
        assertEquals(weatherDataList, result)
    }
}