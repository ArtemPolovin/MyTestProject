package com.example.data.implementationRepo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.dao.WeatherDataDao
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.models.WeatherData
import com.example.domain.repositories.CurrentWeatherRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherRepositoryImpl(
    private val weatherDataApiService: WeatherDataApiService,
    private val mapper: WeatherDataMapper,
    private val weatherDataDao: WeatherDataDao,
    private val weatherDataEntityMapper: WeatherDataEntityMapper
) : CurrentWeatherRepository {

    @ExperimentalStdlibApi
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun getWeatherData(city: String, degreeType: String): Single<WeatherData> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

      return  weatherDataApiService.getCurrentWeatherData(city, degreeType)
          .subscribeOn(Schedulers.io())
          .doOnSuccess { weatherDataDao.insertWeatherData(weatherDataEntityMapper.fromApiToEntity(it)) }
          .map { mapper.mapWeather(it) }
          .onErrorResumeNext {
             weatherDataDao.getWeatherDataFromDb(city,currentDate)
                 .map { weatherDataEntityMapper.fromEntityToWeatherData(it)}
          }
    }


}