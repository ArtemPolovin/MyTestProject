package com.example.data.mappers

import com.example.data.db.tables.LastChosenCitiesEntity
import com.example.domain.models.CityModel
//import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.Assert.assertEquals
import org.junit.Test
//import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LastChosenCitiesEntityMapperTest {

   private  val lastChosenCitiesMapper = LastChosenCitiesEntityMapper()

    @Test
    fun returnLastChosenCitiesEntityFromCityModel() {
        // Given
        val cityModel = CityModel(223, "Moscow", "Russia")
        val lastChosenCitiesEntity = LastChosenCitiesEntity(cityId = 223, city = "Moscow", country = "Russia")

        // When
        val result = lastChosenCitiesMapper.fromCityModelToEntity(cityModel)

        // Then
        assertEquals(lastChosenCitiesEntity,result)
    }

    @Test
    fun returnCityModelListFromEntity() {
        // Given
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

        // When
        val result = lastChosenCitiesMapper.fromEntityToCityModelList(listOfCitiesEntity)

        // Then
        assertEquals(cityModelList,result)
    }
}