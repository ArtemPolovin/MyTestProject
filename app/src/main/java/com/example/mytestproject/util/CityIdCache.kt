package com.example.mytestproject.util

import android.content.SharedPreferences
class CityIdCache(private val sharedPref: SharedPreferences){

    fun loadCityId(): Int {
        return sharedPref.getInt(CITY_ID, DEFAULT_VALUE_NEW_YORK_ID)
    }

    fun saveCityId(cityId: Int) {
        sharedPref.edit().putInt(CITY_ID,cityId).apply()
    }

}


