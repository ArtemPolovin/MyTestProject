package com.example.data.implementationRepo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.apiservice.WeatherDataApiService
import com.example.data.db.dao.WeatherDataDao
import com.example.data.mappers.WeatherDataEntityMapper
import com.example.data.mappers.WeatherDataMapper
import com.example.domain.models.WeatherData
import com.example.domain.repositories.DailyWeatherRepository
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DailyWeatherRepositoryImpl(
    private val apiService: WeatherDataApiService,
    private val mapper: WeatherDataMapper,
     private val weatherDataDao: WeatherDataDao,
    private val weatherDataEntityMapper: WeatherDataEntityMapper
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
            weatherDataDao.getListOfWeatherData(city,getDateList(days))
                .map { weatherDataEntityMapper.fromEntityListToWeatherDataList(it) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    private fun getDateList(days: Int): List<String> {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val list = mutableListOf<String>()
        var localDate = LocalDate.parse(currentDate)

        for (i in 1..days) {
            list.add(localDate.toString())
            localDate = localDate.plusDays(1)
        }
        return list
    }
}