package com.example.mytestproject.data.repository.weatherRepository

import com.example.mytestproject.App
import com.example.mytestproject.ui.weatherData.WeatherDataFragment
import com.example.mytestproject.ui.weatherData.WeatherDataViewModel
import dagger.Component

@Component(modules = [WeatherRepositoryModule::class])
interface WeatherRepositoryComponent {

    fun inject(weatherDataFragment: WeatherDataFragment)

}