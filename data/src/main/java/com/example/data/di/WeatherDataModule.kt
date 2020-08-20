package com.example.data.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.Database
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.implementationRepo.CurrentWeatherRepositoryImpl
import com.example.data.implementationRepo.DailyWeatherRepositoryImpl
import com.example.data.mappers.TimezoneEntityMapper
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
    fun provideContext(): Context = context

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
        weatherDataEntityMapper: WeatherDataEntityMapper,
        timezoneDao: TimezoneDao,
        timezoneEntityMapper: TimezoneEntityMapper
    ): CurrentWeatherRepository =
        CurrentWeatherRepositoryImpl(
            weatherDataApiService,
            mapper,
            weatherDataDao,
            weatherDataEntityMapper,
            timezoneDao,
            timezoneEntityMapper
        )

    @Provides
    @Singleton
    fun provideDailyWeatherRepository(
        mapper: WeatherDataMapper,
        weatherDataApiService: WeatherDataApiService,
        weatherDataDao: WeatherDataDao,
        weatherDataEntityMapper: WeatherDataEntityMapper,
        timezoneDao: TimezoneDao
    ): DailyWeatherRepository = DailyWeatherRepositoryImpl(
        weatherDataApiService,
        mapper,
        weatherDataDao,
        weatherDataEntityMapper,
        timezoneDao
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
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDataDao(database: Database): WeatherDataDao {
        return database.weatherDataDao()
    }

    @Provides
    @Singleton
    fun provideTimezoneDao(database: Database): TimezoneDao {
        return database.timezoneDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDataEntityMapper() = WeatherDataEntityMapper(context)

    @Provides
    @Singleton
    fun provideTimezoneEntityMapper() = TimezoneEntityMapper(context)

    @Provides
    @Singleton
    fun provideActivity() = AppCompatActivity()

}