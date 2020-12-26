package com.example.mytestproject.ui.weatherData.weathercontainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherContainerViewModel : ViewModel() {

    private val _previousFragmentId = MutableLiveData<Int>()
    val previousFragmentId: LiveData<Int> get() = _previousFragmentId

    fun receivePreviousFragmentId(fragmentId: Int) {
        _previousFragmentId.value = fragmentId
    }
}