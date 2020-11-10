package com.example.data.modelsApi.multiDaysWeather

data class DailyData(
    val datetime: String,
    val max_temp: Double,
    val weather: Weather
)