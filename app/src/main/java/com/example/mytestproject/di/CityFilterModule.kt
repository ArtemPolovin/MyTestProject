package com.example.mytestproject.di

import com.example.data.utils.CityConverter
import com.example.mytestproject.util.CityFilter
import dagger.Module
import dagger.Provides

@Module
class CityFilterModule {

    @Provides
    fun provideCityFilter(cityConverter: CityConverter) = CityFilter(cityConverter)

}