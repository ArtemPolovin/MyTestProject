package com.example.domain.repositories

import com.example.domain.models.CityModel
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface LastChosenCitiesRepo {
    fun getLastChosenCities(): Single<List<CityModel>>

    fun insertCityToLastChosenCitiesEntity(cityId: Int,  cityList: List<CityModel>?): Disposable
}