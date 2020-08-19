package com.example.data.implementationRepo

import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.mappers.TimezoneEntityMapper
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.data.utils.getCurrentDateByTimezone
import com.example.domain.models.WeatherData
import com.example.domain.repositories.CurrentWeatherRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CurrentWeatherRepositoryImpl(
    private val weatherDataApiService: WeatherDataApiService,
    private val mapper: WeatherDataMapper,
    private val weatherDataDao: WeatherDataDao,
    private val weatherDataEntityMapper: WeatherDataEntityMapper,
    private val timezoneDao: TimezoneDao,
    private val timezoneEntityMapper: TimezoneEntityMapper
) : CurrentWeatherRepository {


    override fun getWeatherData(city: String, degreeType: String): Single<WeatherData> {

        return weatherDataApiService.getCurrentWeatherData(city, degreeType)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                weatherDataDao.insertWeatherData(weatherDataEntityMapper.fromApiToEntity(it))
                timezoneDao.insertTimezone(timezoneEntityMapper.fromApiToEntity(it))
            }
            .map { mapper.mapWeather(it) }
            .onErrorResumeNext {
                weatherDataDao.getWeatherDataFromDb(
                    city,
                    getCurrentDateByTimezone(timezoneDao.getTimezoneByCityName(city))
                )
                    .map { weatherDataEntityMapper.fromEntityToWeatherData(it) }
            }
    }


}