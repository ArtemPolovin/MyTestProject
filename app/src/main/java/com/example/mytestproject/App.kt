package com.example.mytestproject

import android.app.Application
import com.example.mytestproject.data.repository.DaggerWeatherRepositoryComponent
import com.example.mytestproject.data.repository.WeatherRepositoryComponent

class App : Application() {

    lateinit var appComponent: WeatherRepositoryComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerWeatherRepositoryComponent.create()
    }
}