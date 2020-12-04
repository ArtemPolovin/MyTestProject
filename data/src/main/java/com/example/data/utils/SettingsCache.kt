package com.example.data.utils

import android.content.SharedPreferences

class SettingsCache(private val preferenceManager: SharedPreferences) {

    fun getUnitSystem() =
         preferenceManager.getString(UNIT_SYSTEM, DEFAULT_UNIT_SYSTEM_VALUE) ?: METRIC
}