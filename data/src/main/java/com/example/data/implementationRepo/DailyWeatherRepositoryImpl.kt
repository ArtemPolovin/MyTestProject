package com.example.data.implementationRepo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.data.utils.getDateList
import com.example.domain.models.WeatherData
import com.example.domain.repositories.DailyWeatherRepository
import io.reactivex.Single

class DailyWeatherRepositoryImpl(
    private val apiService: WeatherDataApiService,
    private val mapper: WeatherDataMapper,
     private val weatherDataDao: WeatherDataDao,
    private val weatherDataEntityMapper: WeatherDataEntityMapper,
    private val timezoneDao: TimezoneDao
) : DailyWeatherRepository {


    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDailyWeather(
        city: String,
        days: Int,
        degreeType: String
    ): Single<List<WeatherData>> {
        return apiService.getDailyWeatherData(city, days, degreeType)
              .doOnSuccess { weatherDataDao.insertListOfWeatherData(weatherDataEntityMapper.fromApiToEntityList(it)) }
            .map { mapper.mapToListOfWeather(it) }
        .onErrorResumeNext {
            weatherDataDao.getListOfWeatherData(city,  getDateList(days,timezoneDao.getTimezoneByCityName(city)))
                .map { weatherDataEntityMapper.fromEntityListToWeatherDataList(it) }
        }
    }
}