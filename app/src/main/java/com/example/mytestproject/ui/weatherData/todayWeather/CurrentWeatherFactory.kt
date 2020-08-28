package com.example.mytestproject.ui.weatherData.todayWeather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.mytestproject.util.MySharedPreferences
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CurrentWeatherFactory @Inject constructor(
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    private val mySharedPref: MySharedPreferences
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            return CurrentWeatherViewModel(fetchCurrentWeatherUseCase,mySharedPref) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}