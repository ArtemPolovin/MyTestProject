package com.example.data.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.Database
import com.example.data.db.dao.CityDao
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.implementationRepo.CurrentWeatherRepositoryImpl
import com.example.data.implementationRepo.DailyWeatherRepositoryImpl
import com.example.data.implementationRepo.LastTenChosenCitiesRepoImpl
import com.example.data.mappers.LastTenChosenCitiesEntityMapper
import com.example.data.mappers.TimezoneEntityMapper
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.data.utils.CityConverter
import com.example.domain.repositories.CurrentWeatherRepository
import com.example.domain.repositories.DailyWeatherRepository
import com.example.domain.repositories.LastTenChosenCitiesRepo
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.domain.useCase.weatherData.GetLastTenChosenCitiesUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WeatherDataModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    fun provideFetchWeatherDataUseCase(currentWeatherRepository: CurrentWeatherRepository) =
        FetchCurrentWeatherUseCase(currentWeatherRepository)

    @Provides
    fun provideFetchDailyWeatherUseCase(dailyWeatherRepository: DailyWeatherRepository) =
        FetchDailyWeatherUseCase(dailyWeatherRepository)

    @Provides
    fun provideGetLastTenChosenCitiesUseCase(lastTenChosenCitiesRepo: LastTenChosenCitiesRepo)=
        GetLastTenChosenCitiesUseCase(lastTenChosenCitiesRepo)

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
    fun provideLastTenChosenCitiesRepo(
        cityDao: CityDao,
        mapper: LastTenChosenCitiesEntityMapper
    ): LastTenChosenCitiesRepo = LastTenChosenCitiesRepoImpl(cityDao,mapper)


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
    fun provideCityDao(database: Database): CityDao {
        return database.cityDao()
    }

    @Provides
    fun provideWeatherDataEntityMapper(cityConverter: CityConverter) =
        WeatherDataEntityMapper(cityConverter)

    @Provides
    fun provideTimezoneEntityMapper(cityConverter: CityConverter) =
        TimezoneEntityMapper(cityConverter)

    @Provides
    fun provideLastTenChosenCitiesEntityMapper() = LastTenChosenCitiesEntityMapper()

    @Provides
    @Singleton
    fun provideActivity() = AppCompatActivity()

    @Provides
    @Singleton
    fun provideCityConverter(gson: Gson) = CityConverter(context, gson)

    @Provides
    @Singleton
    fun providGson() = Gson()

}