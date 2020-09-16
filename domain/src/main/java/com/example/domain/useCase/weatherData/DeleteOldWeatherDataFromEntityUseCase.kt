package com.example.domain.useCase.weatherData

import com.example.domain.repositories.DeleteOldWeatherDataRepository

class DeleteOldWeatherDataFromEntityUseCase (
    private val deleteOldWeatherDataRepository: DeleteOldWeatherDataRepository
) {
    operator fun invoke(cityId: Int) {
        deleteOldWeatherDataRepository.deleteOldWeatherDataFromEntity(cityId)
    }
}