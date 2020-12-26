package com.example.mytestproject.ui.weatherData.todayWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.utils.CityDataCache
import com.example.data.utils.SettingsCache
import com.example.domain.useCase.weatherData.FetchCurrentWeatherUseCase
import com.example.mytestproject.viewState.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class CurrentWeatherViewModel(
    private val fetchCurrentWeatherUseCase: FetchCurrentWeatherUseCase,
    private val cityDataCache: CityDataCache,
    settingsCache: SettingsCache
) : ViewModel() {

    private val unitSystem = settingsCache.getUnitSystem()

    private var cityId = cityDataCache.loadCityId()// loading city id from SharedPreferences

    private var disposable: Disposable? = null

    private val _weatherViewState = MutableLiveData<WeatherViewState>()
    val weatherViewState: LiveData<WeatherViewState> get() = _weatherViewState

    init {
        getWeather(cityId)
    }

    private fun getWeather(cityId: Int) { // the method gets current weather data from Api
        _weatherViewState.value = WeatherViewState.Loading

        disposable = fetchCurrentWeatherUseCase(cityId, unitSystem)
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

    fun updateCityData() {
        if (cityDataCache.isCityChanged) {
            cityId = cityDataCache.loadCityId()
            getWeather(cityId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}