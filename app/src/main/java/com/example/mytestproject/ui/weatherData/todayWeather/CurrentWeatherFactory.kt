package com.example.mytestproject.ui.weatherData.todayWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.data.utils.CityDataCache
import com.example.data.utils.SettingsCache
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CurrentWeatherFactory @Inject constructor(
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    private val mySharedPref: CityDataCache,
    private val settingsCache: SettingsCache
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            return CurrentWeatherViewModel(fetchCurrentWeatherUseCase,mySharedPref,settingsCache) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}