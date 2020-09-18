package com.example.mytestproject.ui.weatherData.dailyWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.data.utils.CityDataCache
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DailyWeatherFactory @Inject constructor(
    private val dailyWeatherUseCase: FetchDailyWeatherUseCase,
    private val mySharedPref: CityDataCache
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyWeatherViewModel::class.java)) {
            return DailyWeatherViewModel(dailyWeatherUseCase,mySharedPref) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }

}