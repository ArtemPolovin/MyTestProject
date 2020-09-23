package com.example.data.mappers

import com.example.data.db.tables.LastChosenCitiesEntity
import com.example.domain.models.CityModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LastChosenCitiesEntityMapperTest {

   private  val lastChosenCitiesMapper = LastChosenCitiesEntityMapper()

    @Test
    fun returnLastChosenCitiesEntityFromCityModel() {
        val cityModel = CityModel(223, "Moscow", "Russia")
        assertEquals(LastChosenCitiesEntity(cityId = 223, city = "Moscow", country = "Russia"),
            lastChosenCitiesMapper.fromCityModelToEntity(cityModel)
        )
    }

    @Test
    fun returnCityModelListFromEntity() {
        val listOfCitiesEntity = listOf(
            LastChosenCitiesEntity(cityId = 223, city = "Moscow", country = "Russia"),
            LastChosenCitiesEntity(cityId = 342, city = "New York", country = "USA"),
            LastChosenCitiesEntity(cityId = 113, city = "Kiev", country = "Ukraine")
        )

        val cityModelList = listOf(
            CityModel(223,"Moscow","Russia"),
            CityModel(342,"New York","USA"),
            CityModel(113,"Kiev","Ukraine")
        )

        assertEquals(cityModelList,lastChosenCitiesMapper.fromEntityToCityModelList(listOfCitiesEntity))
    }
}