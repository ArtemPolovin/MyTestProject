package com.example.mytestproject.ui.weatherData.todayWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherDataViewModel(
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState> get() = _viewState

    init {
        getWeather()
    }

    private fun getWeather() {
        _viewState.value = WeatherViewState.Loading

        disposable = fetchCurrentWeatherUseCase("Moscow",  "M")
            .subscribeOn(Schedulers.io())
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
        getWeather()
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}