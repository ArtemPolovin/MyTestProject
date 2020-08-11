package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.entities.WeatherDataEntity
import com.example.domain.models.WeatherData
import io.reactivex.Single


@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherDataEntity: WeatherDataEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfWeatherData(weatherDataList: List<WeatherDataEntity>)

    @Query("SELECT * FROM weather_data WHERE city_name = :cityName AND date = :currentDate")
    fun getWeatherDataFromDb(cityName: String, currentDate: String): Single<WeatherDataEntity>

    @Query("SELECT * FROM weather_data WHERE city_name = :cityName AND date in (:dateList)")
    fun getListOfWeatherData(cityName: String, dateList: List<String>): Single<List<WeatherDataEntity>>

    @Query("DELETE FROM weather_data")
    fun deleteAllFromDb()
}