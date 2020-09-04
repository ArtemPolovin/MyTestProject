package com.example.data.db.dao

import androidx.room.*
import com.example.data.db.tables.LastChosenCitiesEntity
import com.example.domain.models.CityModel
import io.reactivex.Single

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(lastChosenCitiesEntity: LastChosenCitiesEntity)

    @Query("SELECT COUNT(id) FROM last_chosen_cities_entity")
    fun getTableSize(): Int

    @Query("SELECT MIN(id) FROM last_chosen_cities_entity")
    fun getIdOfExtraCity(): Long

    @Query("SELECT * FROM last_chosen_cities_entity")
    fun getAllLastChosenCities(): Single<List<LastChosenCitiesEntity>>

    @Query("DELETE FROM last_chosen_cities_entity WHERE id = (SELECT id FROM last_chosen_cities_entity ORDER BY id LIMIT 1)")
    fun deleteExtraCity()

    @Query("DELETE FROM last_chosen_cities_entity")
    fun deleteAll()

}