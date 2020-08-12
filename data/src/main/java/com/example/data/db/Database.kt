package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.db.tables.TimezoneTable
import com.example.data.db.tables.WeatherDataTable

@Database(
    entities = [WeatherDataTable::class, TimezoneTable::class],
    version = 3,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
    abstract fun timezoneDao(): TimezoneDao

    /*companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `timezone` (`cityName` TEXT NOT NULL DEFAULT null, `time_zone` TEXT NOT NULL DEFAULT null, PRIMARY KEY(`cityName`))")
            }
        }
    }*/


}