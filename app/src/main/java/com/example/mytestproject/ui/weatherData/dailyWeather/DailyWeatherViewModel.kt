package com.example.mytestproject.ui.weatherData.dailyWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.data.utils.CityDataCache
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class DailyWeatherViewModel(
    private val dailyWeatherUseCase: FetchDailyWeatherUseCase,
    cityDataCache: CityDataCache
) : ViewModel() {

    private var numberOfWeatherForecastDays = 0

    private var cityId = cityDataCache.loadCityId()// loading city id from SharedPreferences

    private var disposable: Disposable? = null

    private val _weatherDataViewState = MutableLiveData<WeatherViewState>()
    val weatherDataViewState: LiveData<WeatherViewState> get() = _weatherDataViewState

    private val _cityName =
        MutableLiveData<String>().apply {value = cityDataCache.loadCityName() }
    val cityName: LiveData<String> get() = _cityName

    private fun fetchDailyWeather() { // the method gets list of days with weather data from Api
        _weatherDataViewState.value = WeatherViewState.Loading

        disposable = dailyWeatherUseCase(cityId, numberOfWeatherForecastDays)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _weatherDataViewState.value = WeatherViewState.DailyWeatherLoaded(it)
                }, {
                    _weatherDataViewState.value = WeatherViewState.Error
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    fun daysForForecastWeather(days: Int) { // the method takes the number of days that come from the fragment
        numberOfWeatherForecastDays = days
        fetchDailyWeather()
    }

    fun refreshWeatherDataList() {
        fetchDailyWeather()
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}