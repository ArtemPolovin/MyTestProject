package com.example.mytestproject.ui.weatherData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchWeatherDataUseCase
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class WeatherDataFactory @Inject constructor(
    private val fetchWeatherDataUseCase: FetchWeatherDataUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDataViewModel::class.java)) {
            return WeatherDataViewModel(fetchWeatherDataUseCase) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}