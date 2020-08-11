package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.Database
import com.example.data.db.dao.WeatherDataDao
import com.example.data.implementationRepo.CurrentWeatherRepositoryImpl
import com.example.data.implementationRepo.DailyWeatherRepositoryImpl
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.repositories.CurrentWeatherRepository
import com.example.domain.repositories.DailyWeatherRepository
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideFetchWeatherDataUseCase(currentWeatherRepository: CurrentWeatherRepository) =
        FetchCurrentWeatherUseCase(currentWeatherRepository)

    @Provides
    @Singleton
    fun provideFetchDailyWeatherUseCase(dailyWeatherRepository: DailyWeatherRepository) =
        FetchDailyWeatherUseCase(dailyWeatherRepository)

    @Provides
    @Singleton
    fun providerWeatherDataRepository(
        mapper: WeatherDataMapper,
        weatherDataApiService: WeatherDataApiService,
        weatherDataDao: WeatherDataDao,
        weatherDataEntityMapper: WeatherDataEntityMapper
    ): CurrentWeatherRepository =
        CurrentWeatherRepositoryImpl(
            weatherDataApiService,
            mapper,
            weatherDataDao,
            weatherDataEntityMapper
        )

    @Provides
    @Singleton
    fun provideDailyWeatherRepository(
        mapper: WeatherDataMapper,
        weatherDataApiService: WeatherDataApiService,
        weatherDataDao: WeatherDataDao,
        weatherDataEntityMapper: WeatherDataEntityMapper
    ): DailyWeatherRepository = DailyWeatherRepositoryImpl(
        weatherDataApiService,
        mapper,
        weatherDataDao,
        weatherDataEntityMapper
    )


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