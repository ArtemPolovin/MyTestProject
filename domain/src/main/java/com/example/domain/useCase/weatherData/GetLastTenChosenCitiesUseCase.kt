package com.example.domain.useCase.weatherData

import com.example.domain.models.CityModel
import com.example.domain.repositories.LastTenChosenCitiesRepo
import io.reactivex.Single

class GetLastTenChosenCitiesUseCase(private val lastTenChosenCitiesRepo: LastTenChosenCitiesRepo){
    operator fun invoke(): Single<List<CityModel>> {
        return lastTenChosenCitiesRepo.getLastTenChosenCities()
    }
}
