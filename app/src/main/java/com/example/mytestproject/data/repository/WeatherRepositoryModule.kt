package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import dagger.Module
import dagger.Provides

@Module
class WeatherRepositoryModule {

    @Provides
    fun provideWeatherApi() = ApiService()

    @Provides
    fun provideWeatherRepository(
        mapper: WeatherDataMapper,
        weatherApi: ApiService
    ): WeatherRepository = WeatherRepositoryImpl(mapper, weatherApi)

    @Provides
    fun provideWeatherDataMapper() = WeatherDataMapper()
}