package com.example.data.mappers

import android.annotation.SuppressLint
import com.example.data.db.entities.WeatherDataEntity
import com.example.data.modelsApi.weatherDataApiModel.WeatherDataApiModel
import com.example.domain.models.WeatherData
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataEntityMapper {
    @SuppressLint("SimpleDateFormat")
    fun fromApiToEntity(weatherDataApi: WeatherDataApiModel): WeatherDataEntity {
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        return WeatherDataEntity(
            weatherDataApi.city_name,
            currentDate,
            weatherDataApi.data[0].temp.toString(),
            "https://www.weatherbit.io/static/img/icons/${
            weatherDataApi.data[0].weather.icon}.png"
        )
    }

    fun fromEntityToWeatherData(weatherDataEntity: WeatherDataEntity): Single<WeatherData> {
        return Single.just(WeatherData(
            weatherDataEntity.cityName,
            weatherDataEntity.temperature,
            weatherDataEntity.icon
        ))
    }
}