package com.example.mytestproject.data.repository.weatherDataRepository

import com.example.mytestproject.ui.weatherData.WeatherDataFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherDataModule::class])
interface WeatherDataComponent {

    fun injectWeatherDataFragment(weatherDataFragment: WeatherDataFragment)
}