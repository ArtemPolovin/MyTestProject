package com.example.data.implementationRepo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getWeatherData(cityId: Int, degreeType: String): Single<WeatherData> { // The method takes current weather data from api, then saves the data to SQLite table.
                                                                                        // If there is an error in the request, the method takes data from SQLite table and return it
        return weatherDataApiService.getCurrentWeatherData(cityId, degreeType)
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                weatherDataDao.insertWeatherData(weatherDataEntityMapper.fromApiToEntity(it))
                timezoneDao.insertTimezone(timezoneEntityMapper.fromApiToEntity(it))
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


}