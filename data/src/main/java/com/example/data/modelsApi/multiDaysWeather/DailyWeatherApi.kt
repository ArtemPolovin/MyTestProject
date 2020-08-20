package com.example.data.modelsApi.multiDaysWeather

data class DailyWeatherApi(
    val city_name: String,
    val `data`: List<Data>

)