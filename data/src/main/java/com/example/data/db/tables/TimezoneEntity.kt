package com.example.data.db.tables

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timezone")
class TimezoneEntity(

    @PrimaryKey(autoGenerate = false)
    val cityName: String,
    val time_zone: String)


