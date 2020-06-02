package com.example.mytestproject.data.network.response

data class ResponseTomorrowWeatherData(
    val city_name: String,
    val country_code: String,
    val data: List<Data>,
    val lat: String,
    val lon: String,
    val state_code: String,
    val timezone: String
)