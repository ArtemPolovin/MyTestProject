package com.example.mytestproject.util

import android.content.Context
import com.example.domain.models.CityModel
import com.google.gson.Gson

fun loadCityModel(context: Context): CityModel {

    val sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    val result = sharedPreferences.getString(CITY_MODEL, DEFAULT_VALUE_NEW_YORK_CITY)
    return Gson().fromJson(result, CityModel::class.java)
}