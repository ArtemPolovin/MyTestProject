package com.example.data.di

import com.example.data.apiservice.WeatherDataApiService
import com.example.data.implementationRepo.WeatherDataRepositoryImpl
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.repositories.WeatherDataRepository
import com.example.domain.useCase.weatherData.FetchWeatherDataUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataModule {

    @Provides
    @Singleton
    fun provideFetchWeatherDataUseCase(weatherDataRepository: WeatherDataRepository) =
        FetchWeatherDataUseCase(weatherDataRepository)

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