package com.example.mytestproject.util

import android.content.Context
import android.util.Log
import com.example.domain.models.CityModel
import com.google.gson.Gson
import javax.inject.Inject

class MySharedPreferences(private val context: Context){

    fun loadCityId(): Int {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(CITY_ID, DEFAULT_VALUE_NEW_YORK_ID)
    }

}


