package com.example.mytestproject.di

import android.content.Context
import com.example.mytestproject.util.CityFilter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityFilterModule(private val context: Context) {

    @Provides
    fun provideCityFilter() = CityFilter(context)
}