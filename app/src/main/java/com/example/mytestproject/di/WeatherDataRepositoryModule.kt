package com.example.mytestproject.di

import com.example.data.apiservice.WeatherDataApiService
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.repositories.WeatherDataRepository
import com.example.data.implementationRepo.WeatherDataRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataRepositoryModule {

    @Provides
    @Singleton
    fun providerWeatherDataRepository(
        mapper: WeatherDataMapper,
        weatherDataApiService: WeatherDataApiService
    ): WeatherDataRepository =
        WeatherDataRepositoryImpl(
            weatherDataApiService,
            mapper
        )

    @Provides
    @Singleton
    fun providerApiService() = WeatherDataApiService()

    @Provides
    fun providerMapper() = WeatherDataMapper()
}
