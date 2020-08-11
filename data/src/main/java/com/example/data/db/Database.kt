package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.db.dao.WeatherDataDao
import com.example.data.db.entities.WeatherDataEntity

@Database(entities = [WeatherDataEntity::class],version = 2, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}