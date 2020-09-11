package com.example.domain.repositories

interface DeleteOldWeatherDataRepository {
    fun deleteOldWeatherDataFromEntity(cityId: Int)
}