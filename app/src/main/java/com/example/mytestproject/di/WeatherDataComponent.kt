package com.example.mytestproject.di

import com.example.mytestproject.ui.weatherData.WeatherDataFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherDataModule::class, WeatherDataRepositoryModule::class])
interface WeatherDataComponent {

  fun injectWeatherDataFragment(weatherDataFragment: WeatherDataFragment)

}