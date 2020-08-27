package com.example.mytestproject.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.data.utils.getCityModelsListFromJson
import com.example.domain.models.CityModel
import com.google.gson.Gson
import javax.inject.Inject

class CityFilter @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    private val cityList = mutableListOf<CityModel>()

    init {
        cityList.addAll(getCityModelsListFromJson(context))
    }

    fun filterCityList(inputText: String): List<CityModel> {//  method filters the list of all cities by user-entered characters
        val list = mutableListOf<CityModel>()
        list.addAll( cityList.filter { it.city_name.contains(inputText, true) })
        return list
    }

}