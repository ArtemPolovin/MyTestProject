package com.example.domain.repositories

import com.example.domain.models.CityModel
import io.reactivex.Completable
import io.reactivex.Single

interface LastChosenCitiesRepo {
    fun getLastChosenCities(): Single<List<CityModel>>

    fun insertCityToLastChosenCitiesEntity(cityId: Int,  cityList: List<CityModel>?): Completable
}