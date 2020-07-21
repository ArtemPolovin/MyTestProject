package com.example.mytestproject

import android.app.Application
import com.example.mytestproject.data.repository.weatherDataRepository.DaggerWeatherDataComponent
import com.example.mytestproject.data.repository.weatherDataRepository.WeatherDataComponent

class App : Application() {

    lateinit var weatherDataComponent: WeatherDataComponent

    override fun onCreate() {
        super.onCreate()
        weatherDataComponent = DaggerWeatherDataComponent.create()
    }
}