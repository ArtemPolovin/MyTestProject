package com.example.data.mappers

import com.example.data.db.tables.LastChosenCitiesEntity
import com.example.domain.models.CityModel

class LastChosenCitiesEntityMapper {

    fun fromCityModelToEntity(cityModel: CityModel): LastChosenCitiesEntity { // This method maps CityModel to LastTenChosenCitiesEntity
        val currentTimeInMillis = System.currentTimeMillis()
        return LastChosenCitiesEntity(
            currentTime = currentTimeInMillis,
            city = cityModel.city_name,
            cityId = cityModel.city_id,
            country = cityModel.country_full
        )
    }

    fun fromEntityToCityModelList(listOfCities: List<LastChosenCitiesEntity>): List<CityModel> { // This method maps list of LastTenChosenCitiesEntity to list of CityModels
        return listOfCities.map { CityModel(city_id = it.cityId, city_name = it.city, country_full = it.country) }
    }
}