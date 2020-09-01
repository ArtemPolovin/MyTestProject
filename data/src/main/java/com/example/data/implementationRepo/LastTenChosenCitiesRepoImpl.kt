package com.example.data.implementationRepo

import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastTenChosenCitiesEntityMapper
import com.example.domain.models.CityModel
import com.example.domain.repositories.LastTenChosenCitiesRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LastTenChosenCitiesRepoImpl(
    private val cityDao: CityDao,
    private val mapper: LastTenChosenCitiesEntityMapper
): LastTenChosenCitiesRepo {

    override fun getLastTenChosenCities(): Single<List<CityModel>> {//This method gets list of last ten chosen cities from db and maps the list to list of CityModels
        return cityDao.getAllLastTenChosenCities()
            .subscribeOn(Schedulers.io())
            .map { mapper.fromEntityToCityModelList(it) }
    }
}