package com.example.mytestproject.di

import android.content.Context
import android.content.SharedPreferences
import com.example.data.utils.CityConverter
import com.example.mytestproject.util.CityFilter
import com.example.mytestproject.util.CityIdCache
import com.example.mytestproject.util.SHARED_PREFS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityFilterModule(private val context: Context) {

    @Provides
    fun provideCityFilter(cityConverter: CityConverter) = CityFilter(cityConverter)

    @Provides
    @Singleton
    fun provideMySharedPref(sharePref:SharedPreferences) = CityIdCache(sharePref)

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

}