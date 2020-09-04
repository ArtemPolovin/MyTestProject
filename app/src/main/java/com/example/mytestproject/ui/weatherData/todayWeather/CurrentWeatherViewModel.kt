package com.example.mytestproject.ui.weatherData.todayWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.mytestproject.util.CityIdCache
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CurrentWeatherViewModel(
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    mySharedPref: CityIdCache
) : ViewModel(){

    private var cityId = mySharedPref.loadCityId()// loading city id from SharedPreferences

    private var disposable: Disposable? = null

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState> get() = _viewState

    init {
        getWeather(cityId)
    }

    private fun getWeather(cityId: Int) { // the method gets current weather data from Api
        _viewState.value = WeatherViewState.Loading

        disposable = fetchCurrentWeatherUseCase(cityId,  "I")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _viewState.value = WeatherViewState.CurrentWeatherLoaded(it)
                },
                {
                    _viewState.value = WeatherViewState.Error
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    fun onRetry() {
        getWeather(cityId)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}