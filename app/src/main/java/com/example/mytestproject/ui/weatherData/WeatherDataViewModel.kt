package com.example.mytestproject.ui.weatherData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestproject.data.repository.WeatherRepositoryImpl
import com.example.mytestproject.ui.models.weatherDataModel.WeatherData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherDataViewModel : ViewModel() {

    private val weatherRepository = WeatherRepositoryImpl()

    private var disposable: Disposable? = null

    private val _weatherDataApi: MutableLiveData<WeatherData> =
        MutableLiveData<WeatherData>()
    val weatherDataApi: LiveData<WeatherData> get() = _weatherDataApi

    init {
        Log.i("...........","pass")
            disposable = weatherRepository.getWeatherData("Moscow", 1, "M")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { _weatherDataApi.value = it },
                    { Log.i("ERROR", "error = ${it.localizedMessage}") }
                )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}