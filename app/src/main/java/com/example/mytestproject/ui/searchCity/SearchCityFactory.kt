package com.example.mytestproject.ui.searchCity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastChosenCitiesEntityMapper
import com.example.domain.useCase.cities.GetLastChosenCitiesUseCase
import com.example.domain.useCase.cities.InsertCityToLastChosenCitiesEntityUseCase
import com.example.mytestproject.util.CityFilter
import com.example.mytestproject.util.CityIdCache
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchCityFactory @Inject constructor(
    private val cityFilter: CityFilter,
    private val cityIdCache: CityIdCache,
    private val getLastChosenCitiesUseCase: GetLastChosenCitiesUseCase,
    private val insertCityToLastChosenCitiesEntityUseCase: InsertCityToLastChosenCitiesEntityUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityViewModel::class.java)) {
            return SearchCityViewModel(
                cityFilter,
                cityIdCache,
                getLastChosenCitiesUseCase,
                insertCityToLastChosenCitiesEntityUseCase
            ) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}