package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.WeatherDataEntity
import io.reactivex.Single


@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherDataEntity: WeatherDataEntity)

    @Query("SELECT * FROM weather_data WHERE city_name = :cityName AND date = :currentDate")
    fun getWeatherDataFromDb(cityName: String, currentDate: String): Single<WeatherDataEntity>
}