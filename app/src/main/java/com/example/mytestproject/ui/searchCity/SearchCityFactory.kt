package com.example.mytestproject.ui.searchCity

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastTenChosenCitiesEntityMapper
import com.example.domain.useCase.weatherData.GetLastTenChosenCitiesUseCase
import com.example.mytestproject.util.CityFilter
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchCityFactory @Inject constructor(
    private val cityFilter: CityFilter,
    private val sharePref: SharedPreferences,
    private val cityDao: CityDao,
    private val lastTenChosenCitiesEntityMapper: LastTenChosenCitiesEntityMapper,
    private val getLastTenChosenCitiesUseCase: GetLastTenChosenCitiesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityViewModel::class.java)) {
            return SearchCityViewModel(
                cityFilter,
                sharePref,
                cityDao,
                lastTenChosenCitiesEntityMapper,
                getLastTenChosenCitiesUseCase
            ) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}