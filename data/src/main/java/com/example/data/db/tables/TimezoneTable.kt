package com.example.data.db.tables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.CityModel

@Entity(tableName = "timezone",primaryKeys = ["city_id","city_name"])
class TimezoneTable(

    @Embedded
    val cityModel: CityModel,
    val time_zone: String
)


