package com.example.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "weather_data", primaryKeys = ["city_name","date"])
class WeatherDataEntity(
    @ColumnInfo(name = "city_name")
    val cityName: String,
    val date: String,
    val temperature: String,
    val icon: String,
    val description: String


)