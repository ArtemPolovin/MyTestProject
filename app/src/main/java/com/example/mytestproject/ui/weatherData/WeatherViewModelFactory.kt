package com.example.mytestproject.ui.weatherData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytestproject.data.repository.WeatherRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory @Inject constructor(
  private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherDataViewModel::class.java)) {
            return WeatherDataViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("ViewModel class is not found")
    }
}