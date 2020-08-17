package com.example.mytestproject.ui.weatherData.dailyWeather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.mytestproject.ui.searchCity.SearchCityAdapter
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DailyWeatherFactory @Inject constructor(
    private val dailyWeatherUseCase: FetchDailyWeatherUseCase,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyWeatherViewModel::class.java)) {
            return DailyWeatherViewModel(dailyWeatherUseCase,context) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }

}