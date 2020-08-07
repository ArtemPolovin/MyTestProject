package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.Database
import com.example.data.db.dao.WeatherDataDao
import com.example.data.implementationRepo.WeatherDataRepositoryImpl
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.repositories.WeatherDataRepository
import com.example.domain.useCase.weatherData.FetchWeatherDataUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideFetchWeatherDataUseCase(weatherDataRepository: WeatherDataRepository) =
        FetchWeatherDataUseCase(weatherDataRepository)

    @Provides
    @Singleton
    fun providerWeatherDataRepository(
        mapper: WeatherDataMapper,
        weatherDataApiService: WeatherDataApiService,
        weatherDataDao: WeatherDataDao,
        weatherDataEntityMapper: WeatherDataEntityMapper
    ): WeatherDataRepository =
        WeatherDataRepositoryImpl(weatherDataApiService, mapper,weatherDataDao,weatherDataEntityMapper)

    @Provides
    @Singleton
    fun providerApiService() = WeatherDataApiService()

    @Provides
    fun providerMapper() = WeatherDataMapper()

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.databaseBuilder(context.applicationContext, Database::class.java, "WeatherDB")
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDataDao(database: Database): WeatherDataDao {
        return database.weatherDataDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDataEntityMapper() = WeatherDataEntityMapper()
}