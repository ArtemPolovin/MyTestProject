package com.example.mytestproject.data.repository.weatherRepository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import dagger.Module
import dagger.Provides

@Module
class WeatherRepositoryModule {

    @Provides
    fun provideWeatherMapper() = WeatherDataMapper()

    @Provides
    fun provideWeatherApi() = ApiService()
}