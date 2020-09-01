package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.db.tables.LastTenChosenCitiesEntity
import io.reactivex.Single
import retrofit2.http.DELETE

@Dao
interface CityDao {

    @Insert
    fun insertCity(lastTenChosenCitiesEntity: LastTenChosenCitiesEntity)

    @Query("SELECT COUNT(currentTime) FROM lasttenchosencitiesentity")
    fun getTableSize(): Int

    @Query("DELETE FROM lasttenchosencitiesentity WHERE currentTime = :currentTime")
    fun deleteExtraCity(currentTime: Long)

    @Query("SELECT MIN(currentTime) FROM lasttenchosencitiesentity")
    fun getIdOfExtraCity(): Long

    @Query("SELECT * FROM lasttenchosencitiesentity")
    fun getAllLastTenChosenCities(): Single<List<LastTenChosenCitiesEntity>>


}