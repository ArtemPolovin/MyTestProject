package com.example.mytestproject.util

import com.example.domain.models.WeatherData

interface MyAdapter {
    fun setAdapterData(newWeatherDataList: List<WeatherData>)
}