package com.example.mytestproject

import android.app.Application
import com.example.data.di.WeatherDataModule
import com.example.mytestproject.di.CityFilterModule
import com.example.mytestproject.di.DaggerWeatherDataComponent

import com.example.mytestproject.di.WeatherDataComponent

class App : Application() {

    lateinit var weatherDataComponent: WeatherDataComponent

    override fun onCreate() {
        super.onCreate()
        weatherDataComponent = DaggerWeatherDataComponent.builder()
            .weatherDataModule(WeatherDataModule(applicationContext))
            .cityFilterModule(CityFilterModule(applicationContext))
            .build()


    }
}