package com.example.data.mappers

import com.example.data.db.tables.LastTenChosenCitiesEntity
import com.example.domain.models.CityModel

class LastTenChosenCitiesEntityMapper {

    fun fromCityModelToEntity(cityModel: CityModel): LastTenChosenCitiesEntity { // This method maps CityModel to LastTenChosenCitiesEntity
        val currentTimeInMillis = System.currentTimeMillis()
        return LastTenChosenCitiesEntity(
            currentTime = currentTimeInMillis,
            city = cityModel.city_name,
            cityId = cityModel.city_id,
            country = cityModel.country_full
        )
    }

    fun fromEntityToCityModelList(listOfCities: List<LastTenChosenCitiesEntity>): List<CityModel> { // This method maps list of LastTenChosenCitiesEntity to list of CityModels
        return listOfCities.map { CityModel(city_id = it.cityId, city_name = it.city, country_full = it.country) }
    }
}