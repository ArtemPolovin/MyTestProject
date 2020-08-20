package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.tables.TimezoneEntity

@Dao
interface TimezoneDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimezone(timezone: TimezoneEntity)

    @Query("SELECT time_zone FROM timezone WHERE cityName = :cityName ")
    fun getTimezoneByCityName(cityName: String): String
}