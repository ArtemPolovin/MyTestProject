package com.example.data.modelsApi.multiDaysWeather

data class Data(
    val datetime: String,
    val max_temp: Double,
    val weather: Weather
)