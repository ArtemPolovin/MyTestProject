package com.example.data.modelsApi.currentWeather

data class Data(
    val city_name: String,
    val temp: Double,
    val weather: Weather,
    val datetime: String
)