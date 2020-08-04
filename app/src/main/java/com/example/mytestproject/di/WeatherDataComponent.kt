package com.example.mytestproject.di

import com.example.data.di.WeatherDataModule
import com.example.mytestproject.ui.weatherData.WeatherDataFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherDataModule::class])
interface WeatherDataComponent {

  fun injectWeatherDataFragment(weatherDataFragment: WeatherDataFragment)

}