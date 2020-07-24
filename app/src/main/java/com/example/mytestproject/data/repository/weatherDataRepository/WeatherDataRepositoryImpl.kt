package com.example.mytestproject.data.repository.weatherDataRepository

import com.example.mytestproject.data.network.ApiService
import com.example.mytestproject.data.repository.weatherDataRepository.WeatherDataRepository
import com.example.mytestproject.mapp.mappWeatherDasta.WeatherDataMapper
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.core.Single

class WeatherDataRepositoryImpl (
    private val apiService: ApiService,
    private val mapper: WeatherDataMapper
): WeatherDataRepository {
 override  fun getWeatherData(
        city: String,
        days: Int,
        degreeType: String
    ): Single<WeatherData> =
        apiService.getWeatherData(city, days, degreeType)
            .map { mapper.mapWeather(it) }
}