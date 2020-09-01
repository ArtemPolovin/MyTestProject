package com.example.domain.repositories

import com.example.domain.models.CityModel
import io.reactivex.Single

interface LastTenChosenCitiesRepo {
    fun getLastTenChosenCities(): Single<List<CityModel>>
}