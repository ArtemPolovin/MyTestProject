package com.example.domain.useCase.cities

import com.example.domain.models.CityModel
import com.example.domain.repositories.LastChosenCitiesRepo
import io.reactivex.Single

class GetLastChosenCitiesUseCase(private val lastChosenCitiesRepo: LastChosenCitiesRepo){
    operator fun invoke(): Single<List<CityModel>> {
        return lastChosenCitiesRepo.getLastChosenCities()
    }
}
