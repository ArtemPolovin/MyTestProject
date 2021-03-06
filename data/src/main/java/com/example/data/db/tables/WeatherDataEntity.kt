package com.example.data.db.tables

import androidx.room.Embedded
import androidx.room.Entity
import com.example.domain.models.CityModel

@Entity(tableName = "weather_data", primaryKeys = ["city_id","date"])
data class WeatherDataEntity(

    @Embedded
    val cityModel: CityModel,
    val date: String,
    val temperature: String,
    val icon: String,
    val description: String


)