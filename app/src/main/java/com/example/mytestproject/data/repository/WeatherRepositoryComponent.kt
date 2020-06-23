package com.example.mytestproject.data.repository

import com.example.mytestproject.ui.weatherData.WeatherDataFragment
import dagger.Component

@Component(modules = [WeatherRepositoryModule::class])
interface WeatherRepositoryComponent {

fun injectWeatherDataFragment(weatherDataFragment: WeatherDataFragment)

}