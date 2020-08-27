package com.example.mytestproject.di

import android.content.Context
import com.example.mytestproject.util.CityFilter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CityFilterModule(private val context: Context) {

    @Provides
    fun provideCityFilter(gson: Gson) = CityFilter(context,gson)

    @Provides
    @Singleton
    fun provideGson() = Gson()

}