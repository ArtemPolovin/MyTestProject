package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.CityDao
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.db.tables.LastTenChosenCitiesEntity
import com.example.data.db.tables.TimezoneEntity
import com.example.data.db.tables.WeatherDataEntity

@Database(
    entities = [WeatherDataEntity::class, TimezoneEntity::class,LastTenChosenCitiesEntity::class],
    version = 6,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
    abstract fun timezoneDao(): TimezoneDao
    abstract fun cityDao(): CityDao

    /*companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `timezone` (`cityName` TEXT NOT NULL DEFAULT null, `time_zone` TEXT NOT NULL DEFAULT null, PRIMARY KEY(`cityName`))")
            }
        }
    }*/


}