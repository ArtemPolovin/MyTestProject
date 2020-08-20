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

    override fun getDailyWeather( // The method takes list of days with  weather data from api, then saves the data to SQLite table.
                                  // If there is an error in the request, the method takes data from SQLite table and return it
        cityId: Int,
        days: Int,
        degreeType: String
    ): Single<List<WeatherData>> {
        return apiService.getDailyWeatherData(cityId, days, degreeType)
              .doOnSuccess { weatherDataDao.insertListOfWeatherData(weatherDataEntityMapper.fromApiToEntityList(it)) }
            .map { mapper.mapToListOfWeather(it) }
        .onErrorResumeNext {
            weatherDataDao.getListOfWeatherData(cityId,  getDateList(days,timezoneDao.getTimezoneByCityId(cityId)))
                .map { weatherDataEntityMapper.fromEntityListToWeatherDataList(it) }
        }
    }
}