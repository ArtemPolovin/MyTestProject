package com.example.mytestproject.ui.searchCity

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.models.CityModel
import com.example.mytestproject.util.CITY_ID
import com.example.mytestproject.util.CityFilter
import com.example.mytestproject.util.Event
import io.reactivex.disposables.Disposable

class SearchCityViewModel(
    private val cityFilter: CityFilter,
    private val sharePref: SharedPreferences
) : ViewModel() {

    private val _navigateToCurrentWeather = MutableLiveData<Event<Int>>()
    val navigateToCurrentWeather: LiveData<Event<Int>> get() = _navigateToCurrentWeather

    private val _filteredCityList = MutableLiveData<List<CityModel>>()
    val filteredCityList: LiveData<List<CityModel>> get() = _filteredCityList

    fun searchCity(inputResult: String) {   //  method sends filtered list of "CityModel"  by user-entered characters to adapter
        _filteredCityList.value = cityFilter.filterCityList(inputResult)
    }

    fun onCityChosen(cityId: Int) {
        _navigateToCurrentWeather.value = Event(cityId)

        saveCityId(cityId)
    }

    private fun saveCityId(cityId: Int) { // The method  saves the city id to SharedPreferences
        sharePref.edit().putInt(CITY_ID, cityId).apply()
    }

}