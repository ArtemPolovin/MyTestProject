package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.db.tables.LastChosenCitiesEntity
import io.reactivex.Single

@Dao
interface CityDao {

    @Insert
    fun insertCity(lastChosenCitiesEntity: LastChosenCitiesEntity)

    @Query("SELECT COUNT(currentTime) FROM lastchosencitiesentity")
    fun getTableSize(): Int

    @Query("DELETE FROM lastchosencitiesentity WHERE currentTime = :currentTime")
    fun deleteExtraCity(currentTime: Long)

    @Query("SELECT MIN(currentTime) FROM lastchosencitiesentity")
    fun getIdOfExtraCity(): Long

    @Query("SELECT * FROM lastchosencitiesentity")
    fun getAllLastChosenCities(): Single<List<LastChosenCitiesEntity>>


}