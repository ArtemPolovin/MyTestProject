package com.example.mytestproject.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.data.utils.getCityModelsListFromJson
import com.example.domain.models.CityModel
import com.google.gson.Gson
import javax.inject.Inject

class CityFilter @Inject constructor(private val context: Context) {
    private val cityList = mutableListOf<CityModel>()

    init {
        cityList.addAll(getCityModelsListFromJson(context))
    }

    fun filterCityList(inputText: String): List<CityModel> {//  method filters the list of all cities by user-entered characters
        val list = mutableListOf<CityModel>()
        list.addAll( cityList.filter { it.city_name.contains(inputText, true) })
        saveFilteredCityList(list)
        return list
    }

    private fun saveFilteredCityList(filteredCityList: List<CityModel>) { // method saves filtered list of cities to SharedPreferences
        val jsonCityList = Gson().toJson(filteredCityList)
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putString(FILTERED_LIST, jsonCityList)
        editor?.apply()
    }

}