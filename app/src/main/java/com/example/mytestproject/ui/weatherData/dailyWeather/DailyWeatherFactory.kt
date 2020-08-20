package com.example.mytestproject.ui.weatherData.dailyWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DailyWeatherFactory @Inject constructor (private val dailyWeatherUseCase: FetchDailyWeatherUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyWeatherViewModel::class.java)) {
            return DailyWeatherViewModel(dailyWeatherUseCase) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }

}