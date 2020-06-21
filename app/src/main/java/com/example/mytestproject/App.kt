package com.example.mytestproject

import android.app.Application
import com.example.mytestproject.data.repository.weatherRepository.DaggerWeatherRepositoryComponent
import com.example.mytestproject.data.repository.weatherRepository.WeatherRepositoryComponent
import com.example.mytestproject.data.repository.weatherRepository.WeatherRepositoryImpl

class App : Application() {

    lateinit var appComponent: WeatherRepositoryComponent

    override fun onCreate() {
        super.onCreate()
      appComponent =  DaggerWeatherRepositoryComponent.create()
    }

}