package com.example.domain.useCase.weatherData

import com.example.domain.repositories.IWeatherRepository

class DeleteOldWeatherDataFromEntityUseCase (
    private val weatherRepository: IWeatherRepository
) {
    operator fun invoke() {
        weatherRepository.deleteOldWeatherDataFromEntity()
    }
}