package com.example.data.implementationRepo

import android.annotation.SuppressLint
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.dao.WeatherDataDao
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.models.WeatherData
import com.example.domain.repositories.WeatherDataRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataRepositoryImpl(
    private val weatherDataApiService: WeatherDataApiService,
    private val mapper: WeatherDataMapper,
    private val weatherDataDao: WeatherDataDao,
    private val weatherDataEntityMapper: WeatherDataEntityMapper
) : WeatherDataRepository {

    @SuppressLint("SimpleDateFormat")
    override fun getWeatherData(city: String, days: Int, degreeType: String): Single<WeatherData> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

      return  weatherDataApiService.getWeatherData(city, days, degreeType)
          .subscribeOn(Schedulers.io())
          .doOnSuccess { weatherDataDao.insertWeatherData(weatherDataEntityMapper.fromApiToEntity(it)) }
          .map { mapper.mapWeather(it) }
          .onErrorResumeNext { weatherDataEntityMapper.fromEntityToWeatherData(weatherDataDao.getWeatherDataFromDb(city,currentDate)) }
    }


}