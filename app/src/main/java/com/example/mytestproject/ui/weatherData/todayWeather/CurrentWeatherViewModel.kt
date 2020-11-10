package com.example.mytestproject.ui.weatherData.todayWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.data.utils.CityDataCache
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CurrentWeatherViewModel(
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    cityDataCache: CityDataCache
) : ViewModel(){

    private var cityId = cityDataCache.loadCityId()// loading city id from SharedPreferences

    private var disposable: Disposable? = null

    private val _weatherViewState = MutableLiveData<WeatherViewState>()
    val weatherViewState: LiveData<WeatherViewState> get() = _weatherViewState

    init {
        getWeather(cityId)
        _weatherViewState.value = WeatherViewState.Loading
    }

    private fun getWeather(cityId: Int) { // the method gets current weather data from Api
        _weatherViewState.value = WeatherViewState.Loading

        disposable = fetchCurrentWeatherUseCase(cityId,  "I")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _weatherViewState.value = WeatherViewState.CurrentWeatherLoaded(it)
                },
                {
                    _weatherViewState.value = WeatherViewState.Error
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    fun onRetry() {
        getWeather(cityId)
    }

    fun refreshWeatherDataList() {
        getWeather(cityId)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}