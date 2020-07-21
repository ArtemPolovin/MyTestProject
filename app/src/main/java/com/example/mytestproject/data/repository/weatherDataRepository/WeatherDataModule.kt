package com.example.mytestproject.data.repository.weatherDataRepository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataModule {

    @Provides
    @Singleton
    fun providerWeatherDataRepository(
        mapper: WeatherDataMapper,
        apiService: ApiService
    ): WeatherDataRepository = WeatherDataRepositoryImpl(apiService, mapper)

    @Provides
    @Singleton
    fun providerApiService() = ApiService()

    @Provides
    fun providerMapper() = WeatherDataMapper()
}