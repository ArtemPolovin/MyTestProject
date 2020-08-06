package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.WeatherDataDao
import com.example.data.db.entities.WeatherDataEntity

@Database(entities = [WeatherDataEntity::class],version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}