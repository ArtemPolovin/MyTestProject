package com.example.mytestproject.ui.weatherData.dailyWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.mytestproject.util.CityDataCache
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class DailyWeatherViewModel(
    private val dailyWeatherUseCase: FetchDailyWeatherUseCase,
    mySharedPref: CityDataCache
) : ViewModel() {

    private var numberOfWeatherForecastDays = 0

    private var cityId = mySharedPref.loadCityId()// loading city id from SharedPreferences

    private var disposable: Disposable? = null

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState> get() = _viewState

    private val _cityName =
        MutableLiveData<String>().apply {value = mySharedPref.loadCityName() }
    val cityName: LiveData<String> get() = _cityName

    private fun fetchDailyWeather() { // the method gets list of days with weather data from Api
        _viewState.value = WeatherViewState.Loading

        disposable = dailyWeatherUseCase(cityId, numberOfWeatherForecastDays, "I")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _viewState.value = WeatherViewState.DailyWeatherLoaded(it)
                }, {
                    _viewState.value = WeatherViewState.Error
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