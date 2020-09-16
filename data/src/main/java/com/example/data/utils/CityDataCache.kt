package com.example.data.utils

import android.content.SharedPreferences

class CityDataCache(
    private val sharedPref: SharedPreferences
){

    fun loadCityId(): Int {
        return sharedPref.getInt(CITY_ID, DEFAULT_VALUE_NEW_YORK_ID)
    }

    fun saveCityId(cityId: Int) {
        sharedPref.edit().putInt(CITY_ID,cityId).apply()
    }

    fun saveCityName(cityName: String) {
        sharedPref.edit().putString(CITY_NAME,cityName).apply()
    }

    fun loadCityName(): String {
        return sharedPref.getString(CITY_NAME, DEFAULT_VALUE_NEW_YORK_NAME) ?: "New York City"
    }

}


