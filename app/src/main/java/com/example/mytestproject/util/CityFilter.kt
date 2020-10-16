package com.example.mytestproject.util

import com.example.data.utils.CityConverter
import com.example.domain.models.CityModel
import javax.inject.Inject
 class CityFilter @Inject constructor(cityConverter: CityConverter) {
    private val cityList = mutableListOf<CityModel>()

    init {
        cityList.addAll(cityConverter.getCityModelsListFromJson())
    }

    fun filterCityList(inputText: String): List<CityModel> {//  method filters the list of all cities by user-entered characters
        return cityList.filter { it.city_name.contains(inputText, true) }
    }

}