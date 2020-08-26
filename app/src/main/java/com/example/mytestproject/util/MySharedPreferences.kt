package com.example.mytestproject.util

import android.content.Context
import android.util.Log
import com.example.domain.models.CityModel
import com.google.gson.Gson

fun loadCityId(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    return sharedPreferences.getInt(CITY_ID, DEFAULT_VALUE_NEW_YORK_ID)
}

fun loadCityName(context: Context) : String{ // method gets filtered list of "CityModels" from SharedPreferences and searches for the name of city that a user-selected
    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    val listOfFilteredCities =
        sharedPreferences.getString(FILTERED_LIST, DEFAULT_VALUE_NEW_YORK_CITY_NAME)
    val filteredCityList = Gson().fromJson(listOfFilteredCities, Array<CityModel>::class.java)
    return filteredCityList.filter { it.city_id == loadCityId(context) }[0].city_name   // "loadCityId()" should return the id of the city that the user has selected
}
