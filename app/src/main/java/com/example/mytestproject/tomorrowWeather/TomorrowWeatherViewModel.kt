package com.example.mytestproject.tomorrowWeather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestproject.data.network.response.ResponseTomorrowWeatherData
import com.example.mytestproject.data.repository.TomorrowWeatherRepository
import com.example.mytestproject.data.repository.TomorrowWeatherRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TomorrowWeatherViewModel : ViewModel() {

    private val tomorrowWeatherRepository = TomorrowWeatherRepositoryImpl()

    private var disposable: Disposable? = null

    private val _tomorrowWeatherData: MutableLiveData<ResponseTomorrowWeatherData> =
        MutableLiveData<ResponseTomorrowWeatherData>()
    val tomorrowWeatherData: LiveData<ResponseTomorrowWeatherData> get() = _tomorrowWeatherData

    fun getTomorrowWeatherData(key: String, city: String, days: Int, degreeType: String) {
        disposable = tomorrowWeatherRepository.getTomorrowWeatherData(key,city, days,degreeType)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {_tomorrowWeatherData.value = it },
                { Log.i("ERROR","error = ${it.localizedMessage}")}
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}