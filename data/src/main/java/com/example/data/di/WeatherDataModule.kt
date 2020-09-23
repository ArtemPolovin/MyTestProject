package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.Database
import com.example.data.db.dao.CityDao
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.implementationRepo.LastChosenCitiesRepoImpl
import com.example.data.implementationRepo.WeatherRepositoryImpl
import com.example.data.mappers.LastChosenCitiesEntityMapper
import com.example.data.mappers.TimezoneEntityMapper
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.data.utils.CityConverter
import com.example.data.utils.CityDataCache
import com.example.data.utils.SHARED_PREFS
import com.example.domain.repositories.IWeatherRepository
import com.example.domain.repositories.LastChosenCitiesRepo
import com.example.domain.useCase.cities.GetLastChosenCitiesUseCase
import com.example.domain.useCase.cities.InsertCityToLastChosenCitiesEntityUseCase
import com.example.domain.useCase.weatherData.DeleteOldWeatherDataFromEntityUseCase
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class WeatherDataModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    fun provideFetchWeatherDataUseCase(weatherRepository: IWeatherRepository) =
        FetchCurrentWeatherUseCase(weatherRepository)

    @Provides
    fun provideFetchDailyWeatherUseCase(weatherRepository: IWeatherRepository) =
        FetchDailyWeatherUseCase(weatherRepository)

    @Provides
    fun provideGetLastChosenCitiesUseCase(lastChosenCitiesRepo: LastChosenCitiesRepo) =
        GetLastChosenCitiesUseCase(lastChosenCitiesRepo)

    @Provides
    fun provideInsertCityToLastChosenCitiesEntityUseCase(lastChosenCitiesRepo: LastChosenCitiesRepo) =
        InsertCityToLastChosenCitiesEntityUseCase(lastChosenCitiesRepo)

    @Provides
    fun provideDeleteOldWeatherDataFromEntityUseCase(weatherRepository: IWeatherRepository) =
        DeleteOldWeatherDataFromEntityUseCase(weatherRepository)


    @Provides
    @Singleton
    fun provideWeatherRepositoryImpl(
        weatherDataApiService: WeatherDataApiService,
        mapper: WeatherDataMapper,
        weatherDataDao: WeatherDataDao,
        weatherDataEntityMapper: WeatherDataEntityMapper,
        timezoneDao: TimezoneDao,
        timezoneEntityMapper: TimezoneEntityMapper,
        @Named("io")
        schedulersIO: Scheduler,
        apiService: WeatherDataApiService,
        cityDataCache: CityDataCache
    ): IWeatherRepository = WeatherRepositoryImpl(
        weatherDataApiService,
        mapper,
        weatherDataDao,
        weatherDataEntityMapper,
        timezoneDao,
        timezoneEntityMapper,
        schedulersIO,
        apiService,
        cityDataCache
    )

    @Provides
    @Singleton
    fun provideLastChosenCitiesRepo(
        cityDao: CityDao,
        mapper: LastChosenCitiesEntityMapper,
        database: Database,
        @Named("io")
        schedulersIO: Scheduler
    ): LastChosenCitiesRepo = LastChosenCitiesRepoImpl(cityDao, mapper, database, schedulersIO)


    @Provides
    @Singleton
    fun providerApiService() = WeatherDataApiService()

    @Provides
    fun providerMapper() = WeatherDataMapper()

    @Provides
    @Singleton
    fun provideDatabase(): Database {
        return Room.databaseBuilder(context.applicationContext, Database::class.java, "WeatherDB")
            //.fallbackToDestructiveMigration()
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
    fun provideLastChosenCitiesEntityMapper() = LastChosenCitiesEntityMapper()

    @Provides
    @Singleton
    fun provideActivity() = AppCompatActivity()

    @Provides
    @Singleton
    fun provideCityConverter(context: Context,gson: Gson) = CityConverter(context, gson)

    @Provides
    @Singleton
    fun providGson() = Gson()

    @Provides
    @Named("io")
    fun provideSchedulersIO(): Scheduler = Schedulers.io()

    @Provides
    @Singleton
    fun provideCityDataCache(sharePref: SharedPreferences) = CityDataCache(sharePref)

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

}