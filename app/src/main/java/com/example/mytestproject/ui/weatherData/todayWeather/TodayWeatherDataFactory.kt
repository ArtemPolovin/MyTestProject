package com.example.mytestproject.ui.weatherData.todayWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchWeatherDataUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TodayWeatherDataFactory @Inject constructor(
    private val fetchWeatherDataUseCase: FetchWeatherDataUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodayWeatherDataViewModel::class.java)) {
            return TodayWeatherDataViewModel(
                fetchWeatherDataUseCase
            ) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}