package com.example.mytestproject.ui.searchCity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.useCase.cities.GetLastChosenCitiesUseCase
import com.example.domain.useCase.cities.InsertCityToLastChosenCitiesEntityUseCase
import com.example.mytestproject.util.CityFilter
import com.example.data.utils.CityDataCache
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SearchCityFactory @Inject constructor(
    private val cityFilter: CityFilter,
    private val cityDataCache: CityDataCache,
    private val getLastChosenCitiesUseCase: GetLastChosenCitiesUseCase,
    private val insertCityToLastChosenCitiesEntityUseCase: InsertCityToLastChosenCitiesEntityUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchCityViewModel::class.java)) {
            return SearchCityViewModel(
                cityFilter,
                cityDataCache,
                getLastChosenCitiesUseCase,
                insertCityToLastChosenCitiesEntityUseCase
            ) as T
        }
        throw IllegalArgumentException("ViewModel was not found")
    }
}