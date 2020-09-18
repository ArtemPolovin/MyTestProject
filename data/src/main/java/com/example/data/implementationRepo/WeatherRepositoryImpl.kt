package com.example.data.implementationRepo

import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.mappers.TimezoneEntityMapper
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.data.utils.CityDataCache
import com.example.data.utils.getCurrentDateByTimezone
import com.example.data.utils.getDateList
import com.example.domain.models.WeatherData
import com.example.domain.repositories.IWeatherRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class WeatherRepositoryImpl(
    private val weatherDataApiService: WeatherDataApiService,
    private val mapper: WeatherDataMapper,
    private val weatherDataDao: WeatherDataDao,
    private val weatherDataEntityMapper: WeatherDataEntityMapper,
    private val timezoneDao: TimezoneDao,
    private val timezoneEntityMapper: TimezoneEntityMapper,
    private val schedulersIO: Scheduler,
    private val apiService: WeatherDataApiService,
    private val cityDataCache: CityDataCache
): IWeatherRepository {

    override fun getWeatherData(cityId: Int, degreeType: String): Single<WeatherData> { // The method takes current weather data from api, then saves the data to SQLite table.
                                                                                        // If there is an error in the request, the method takes data from SQLite table and return it
        return weatherDataApiService.getCurrentWeatherData(cityId, degreeType)
            .subscribeOn(schedulersIO)
            .doOnSuccess {
                weatherDataDao.insertWeatherData(weatherDataEntityMapper.fromApiToEntity(it,cityId))
                timezoneDao.insertTimezone(timezoneEntityMapper.fromApiToEntity(it,cityId))
            }
            .map { mapper.mapWeather(it) }
            .onErrorResumeNext {
                weatherDataDao.getWeatherDataFromDb(
                    cityId,
                    getCurrentDateByTimezone(timezoneDao.getTimezoneByCityId(cityId))
                )
                    .map { weatherDataEntityMapper.fromEntityToWeatherData(it) }
            }
    }

    override fun getDailyWeather( // The method takes list of days with  weather data from api, then saves the data to SQLite table.
        // If there is an error in the request, the method takes data from SQLite table and return it
        cityId: Int,
        days: Int,
        degreeType: String
    ): Single<List<WeatherData>> {
        return apiService.getDailyWeatherData(cityId, days, degreeType)
            .subscribeOn(schedulersIO)
            .doOnSuccess { weatherDataDao.insertListOfWeatherData(weatherDataEntityMapper.fromApiToEntityList(it,cityId)) }
            .map { mapper.mapToListOfWeather(it) }
            .onErrorResumeNext {
                weatherDataDao.getListOfWeatherData(cityId,  getDateList(days,timezoneDao.getTimezoneByCityId(cityId)))
                    .map { weatherDataEntityMapper.fromEntityListToWeatherDataList(it) }
            }
    }

    override fun deleteOldWeatherDataFromEntity() {
        val currentDate =
            getCurrentDateByTimezone(timezoneDao.getTimezoneByCityId(cityDataCache.loadCityId()))
        weatherDataDao.deleteOldWeatherData(currentDate)
    }


}