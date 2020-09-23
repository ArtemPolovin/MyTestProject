package com.example.data.db.tables

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "last_chosen_cities_entity",
    indices = [Index(value = ["cityId"], unique = true)]
)
data class LastChosenCitiesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityId: Int,
    val city: String,
    val country: String
)
