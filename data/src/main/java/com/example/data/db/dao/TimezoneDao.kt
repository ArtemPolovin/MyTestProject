package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.tables.TimezoneTable

@Dao
interface TimezoneDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTimezone(timezone: TimezoneTable)

    @Query("SELECT time_zone FROM timezone WHERE city_id = :cityId ")
    fun getTimezoneByCityId(cityId: Int): String
}