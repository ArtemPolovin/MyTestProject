package com.example.mytestproject.di

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
}