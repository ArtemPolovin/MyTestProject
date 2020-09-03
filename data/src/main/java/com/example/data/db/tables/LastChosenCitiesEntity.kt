package com.example.data.db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class LastChosenCitiesEntity(
    @PrimaryKey(autoGenerate = false)
    val currentTime: Long,
    val city: String,
    val cityId: Int,
    val country: String
)
