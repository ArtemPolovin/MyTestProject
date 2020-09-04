package com.example.domain.useCase.cities

import com.example.domain.models.CityModel
import com.example.domain.repositories.LastChosenCitiesRepo
import io.reactivex.Completable
import io.reactivex.disposables.Disposable

class InsertCityToLastChosenCitiesEntityUseCase(private val lastChosenCitiesRepo: LastChosenCitiesRepo){
    operator fun invoke(cityId: Int, cityList: List<CityModel>?) : Completable {
       return  lastChosenCitiesRepo.insertCityToLastChosenCitiesEntity(cityId, cityList)
    }
}
