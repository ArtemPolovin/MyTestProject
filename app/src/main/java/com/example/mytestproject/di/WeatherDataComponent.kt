package com.example.mytestproject.di

import com.example.data.di.WeatherDataModule
import com.example.mytestproject.ui.weatherData.dailyWeather.TenDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.dailyWeather.ThreeDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.todayWeather.WeatherDataFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherDataModule::class])
interface WeatherDataComponent {

  fun inject(weatherDataFragment: WeatherDataFragment)
  fun inject(threeDaysWeatherFragment: ThreeDaysWeatherFragment)
  fun inject(tenDaysWeatherFragment: TenDaysWeatherFragment)

}