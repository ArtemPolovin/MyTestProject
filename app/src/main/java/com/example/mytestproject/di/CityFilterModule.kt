package com.example.mytestproject.di

import android.content.Context
import com.example.data.utils.CityConverter
import com.example.mytestproject.util.CityFilter
import com.example.mytestproject.util.MySharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityFilterModule(private val context: Context) {

    @Provides
    fun provideCityFilter(cityConverter: CityConverter) = CityFilter(cityConverter)

    @Provides
    @Singleton
    fun provideMySharedPref() = MySharedPreferences(context)

}