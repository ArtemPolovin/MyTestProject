package com.example.data.mappers

import com.example.data.modelsApi.currentWeather.CurrentWeatherApiModel
import com.example.data.modelsApi.currentWeather.Data
import com.example.data.modelsApi.currentWeather.Weather
import com.example.data.modelsApi.multiDaysWeather.DailyData
import com.example.data.modelsApi.multiDaysWeather.DailyWeatherApi
import com.example.data.utils.FAHRENHEIT_TYPE
import com.example.data.utils.ICON_URL
import com.example.data.utils.IMPERIAL
import com.example.data.utils.SettingsCache
import com.example.domain.models.WeatherData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WeatherDailyDataMapperTest {


    @Mock
    private lateinit var settingsCache: SettingsCache

    private lateinit var weatherDataMapper : WeatherDataMapper

    private val unitSystem = IMPERIAL

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

      `when`(settingsCache.getUnitSystem()).thenReturn(unitSystem)

        weatherDataMapper = WeatherDataMapper(settingsCache)
    }



    @Test
    fun returnWeatherDataObject() {
        // Given
        val weather = Weather("800", "Clear sky", "c01d")
        val data = Data("New York City", 52.3, weather, "2020-09-22", "America/New_York")
        val dataList = listOf(data)
        val apiModel = CurrentWeatherApiModel(dataList)

        val weatherData =
            WeatherData("New York City", "52", "${ICON_URL}c01d.png", "2020-09-22", "Clear sky",
                FAHRENHEIT_TYPE)

        // When
        val result = weatherDataMapper.mapApiToWeatherDataModel(apiModel)

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
            WeatherData("Moscow", "63", "${ICON_URL}c02d.png", "Wednesday, 23", "Few clouds",
                FAHRENHEIT_TYPE),
            WeatherData("Moscow", "74", "${ICON_URL}c04d.png", "Thursday, 24", "Overcast clouds",
                FAHRENHEIT_TYPE)
        )

        // When
        val result = weatherDataMapper.mapToListOfDailyWeatherData(dailyWeatherApi)

        // Then
        assertEquals(weatherDataList, result)
    }
}