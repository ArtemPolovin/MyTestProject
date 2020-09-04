package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.CityDao
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.db.tables.LastChosenCitiesEntity
import com.example.data.db.tables.TimezoneEntity
import com.example.data.db.tables.WeatherDataEntity

@Database(
    entities = [WeatherDataEntity::class, TimezoneEntity::class, LastChosenCitiesEntity::class],
    version = 11,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
    abstract fun timezoneDao(): TimezoneDao
    abstract fun cityDao(): CityDao
}