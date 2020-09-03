package com.example.mytestproject.ui.searchCity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastChosenCitiesEntityMapper
import com.example.domain.models.CityModel
import com.example.domain.useCase.cities.GetLastChosenCitiesUseCase
import com.example.domain.useCase.cities.InsertCityToLastChosenCitiesEntityUseCase
import com.example.mytestproject.util.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchCityViewModel(
    private val cityFilter: CityFilter,
    private val cityIdCache: CityIdCache,
    private val getLastChosenCitiesUseCase: GetLastChosenCitiesUseCase,
    private val insertCityToLastChosenCitiesEntityUseCase: InsertCityToLastChosenCitiesEntityUseCase
) : ViewModel() {

    private var disposeBag = CompositeDisposable()

    private val _lastChosenCities = MutableLiveData<List<CityModel>>()
    val lastChosenCities: LiveData<List<CityModel>> get() = _lastChosenCities

    private val _navigateToCurrentWeather = MutableLiveData<Event<Int>>()
    val navigateToCurrentWeather: LiveData<Event<Int>> get() = _navigateToCurrentWeather

    private val _filteredCityList = MutableLiveData<List<CityModel>>()
    val filteredCityList: LiveData<List<CityModel>> get() = _filteredCityList

    init {
        getLastChosenCities()
    }

    fun searchCity(inputResult: String) {   //  method sends filtered list of "CityModel"  by user-entered characters to adapter
        _filteredCityList.value = cityFilter.filterCityList(inputResult)
    }

    fun onCityChosen(cityId: Int) {
        _navigateToCurrentWeather.value = Event(cityId)
        saveCityId(cityId)
        insertCityToEntity(cityId)
    }

    private fun saveCityId(cityId: Int) { // The method  saves the city id to SharedPreferences
        cityIdCache.saveCityId(cityId)
    }

    private fun insertCityToEntity(cityId: Int) { // This method inserts chosen city to db table

       disposeBag.add(insertCityToLastChosenCitiesEntityUseCase(cityId, filteredCityList.value))

    }

    private fun getLastChosenCities() { // This method gets list of  last ten chosen cities from db and assigns this list to LiveData
       val  disposable = getLastChosenCitiesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _lastChosenCities.value = it.reversed()
                },
                {
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
        disposeBag.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }

}