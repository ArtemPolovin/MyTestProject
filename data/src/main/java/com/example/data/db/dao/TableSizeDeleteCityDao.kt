package com.example.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class TableSizeDeleteCityDao {

    @Query("SELECT COUNT(id) FROM last_chosen_cities_entity")
    abstract fun getTableSize(): Int

    @Query("DELETE FROM last_chosen_cities_entity WHERE id = (SELECT id FROM last_chosen_cities_entity ORDER BY id LIMIT 1)")
    abstract fun deleteExtraCity()

    @Transaction
    open  fun getTableSizeAndDeleteExtraCity(){
        getTableSize()
        deleteExtraCity()
    }




}