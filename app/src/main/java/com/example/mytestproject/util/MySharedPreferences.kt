package com.example.mytestproject.util

import android.content.SharedPreferences
class MySharedPreferences(private val sharePref: SharedPreferences){

    fun loadCityId(): Int {
        return sharePref.getInt(CITY_ID, DEFAULT_VALUE_NEW_YORK_ID)
    }

}


