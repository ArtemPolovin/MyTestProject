package com.example.mytestproject.di

import com.example.data.di.WeatherDataModule
import com.example.mytestproject.ui.searchCity.SearchCityFragment
import com.example.mytestproject.ui.searchCity.SearchCityViewModel
import com.example.mytestproject.ui.weatherData.dailyWeather.TenDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.dailyWeather.ThreeDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.todayWeather.CurrentWeatherFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherDataModule::class, CityFilterModule::class])
interface WeatherDataComponent {

  fun inject(currentWeatherFragment: CurrentWeatherFragment)
  fun inject(threeDaysWeatherFragment: ThreeDaysWeatherFragment)
  fun inject(tenDaysWeatherFragment: TenDaysWeatherFragment)
  fun inject(searchCityViewModel: SearchCityViewModel)
  fun inject(searchCityFragment: SearchCityFragment)

}