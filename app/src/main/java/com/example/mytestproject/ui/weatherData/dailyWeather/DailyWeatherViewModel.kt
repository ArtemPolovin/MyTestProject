package com.example.mytestproject.ui.weatherData.dailyWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DailyWeatherViewModel(private val dailyWeatherUseCase: FetchDailyWeatherUseCase) : ViewModel() {

    private var disposable: Disposable? = null

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState> get() = _viewState

    private fun fetchDailyWeather(days: Int) {
        _viewState.value = WeatherViewState.Loading

       disposable =  dailyWeatherUseCase("San Diego", days, "M")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _viewState.value = WeatherViewState.DailyWeatherLoaded(it)
                },{
                    _viewState.value = WeatherViewState.Error
                    Log.i("ERROR","error = ${it.printStackTrace()}")
                }
            )
    }

    fun getWeatherData(days: Int) {
       fetchDailyWeather(days)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}