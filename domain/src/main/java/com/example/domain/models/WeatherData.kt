package com.example.domain.models

data class WeatherData(
    val city_name: String,
    val temp: String,
    val icon: String,
    val date: String,
    val description: String,
    val temperatureType: String
)