package com.example.data.db.tables

import androidx.room.Embedded
import androidx.room.Entity
import com.example.domain.models.CityModel

@Entity(tableName = "timezone",primaryKeys = ["city_id","city_name"])
class TimezoneEntity(

    @Embedded
    val cityModel: CityModel,
    val time_zone: String
)


