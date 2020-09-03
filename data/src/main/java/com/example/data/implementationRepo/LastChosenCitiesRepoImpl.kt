package com.example.data.implementationRepo

import android.util.Log
import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastChosenCitiesEntityMapper
import com.example.domain.models.CityModel
import com.example.domain.repositories.LastChosenCitiesRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LastChosenCitiesRepoImpl(
    private val cityDao: CityDao,
    private val mapper: LastChosenCitiesEntityMapper
): LastChosenCitiesRepo {

    override fun getLastChosenCities(): Single<List<CityModel>> {//This method gets list of last ten chosen cities from db and maps the list to list of CityModels
        return cityDao.getAllLastChosenCities()
            .subscribeOn(Schedulers.io())
            .map { mapper.fromEntityToCityModelList(it) }
    }
}