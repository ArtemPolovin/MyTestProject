package com.example.mytestproject.ui.weatherData.dailyWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.useCase.weatherData.FetchDailyWeatherUseCase
import com.example.mytestproject.util.CityIdCache
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DailyWeatherViewModel(
    private val dailyWeatherUseCase: FetchDailyWeatherUseCase,
    mySharedPref: CityIdCache
) : ViewModel() {

    private var cityId = mySharedPref.loadCityId()// loading city id from SharedPreferences

    private var disposable: Disposable? = null

    private val _viewState = MutableLiveData<WeatherViewState>()
    val viewState: LiveData<WeatherViewState> get() = _viewState

    private val _cityName = MutableLiveData<String>()
    val cityName : LiveData<String> get() = _cityName

    private fun fetchDailyWeather(days: Int) { // the method gets list of days with weather data from Api
        _viewState.value = WeatherViewState.Loading

        disposable = dailyWeatherUseCase(cityId, days, "I")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _viewState.value = WeatherViewState.DailyWeatherLoaded(it)
                    _cityName.value = it[0].city_name
                }, {
                    _viewState.value = WeatherViewState.Error
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    fun getWeatherData(days: Int) { // the method takes the number of days that come from the fragment
        fetchDailyWeather(days)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}