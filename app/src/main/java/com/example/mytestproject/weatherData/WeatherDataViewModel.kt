package com.example.mytestproject.weatherData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestproject.data.network.response.WeatherDataApi
import com.example.mytestproject.data.repository.WeatherRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherDataViewModel : ViewModel() {

    private val weatherRepository = WeatherRepositoryImpl()

    private var disposable: Disposable? = null

    private val _weatherDataApi: MutableLiveData<WeatherDataApi> =
        MutableLiveData<WeatherDataApi>()
    val weatherDataApi: LiveData<WeatherDataApi> get() = _weatherDataApi

    fun getWeatherData(key: String, city: String, days: Int, degreeType: String) {
        disposable = weatherRepository.getWeatherData(key,city, days,degreeType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {_weatherDataApi.value = it },
                { Log.i("ERROR","error = ${it.localizedMessage}")}
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}