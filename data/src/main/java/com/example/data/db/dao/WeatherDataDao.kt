package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.db.tables.WeatherDataTable
import io.reactivex.Single


@Dao
interface WeatherDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherDataTable: WeatherDataTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfWeatherData(weatherDataList: List<WeatherDataTable>)

    @Query("SELECT * FROM weather_data WHERE city_name = :cityName AND date = :currentDate")
    fun getWeatherDataFromDb(cityName: String, currentDate: String): Single<WeatherDataTable>

    @Query("SELECT * FROM weather_data WHERE city_name = :cityName AND date in (:dateList)")
    fun getListOfWeatherData(cityName: String, dateList: List<String>): Single<List<WeatherDataTable>>

    @Query("DELETE FROM weather_data")
    fun deleteAllFromWeatherDataTable()
}