package com.example.data.implementationRepo

import com.example.data.db.Database
import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastChosenCitiesEntityMapper
import com.example.data.utils.MAX_TABLE_SIZE
import com.example.domain.models.CityModel
import com.example.domain.repositories.LastChosenCitiesRepo
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single

class LastChosenCitiesRepoImpl(
    private val cityDao: CityDao,
    private val mapper: LastChosenCitiesEntityMapper,
    private val database: Database,
    private val schedulersIO: Scheduler
) : LastChosenCitiesRepo {

    override fun getLastChosenCities(): Single<List<CityModel>> {//This method gets list of last ten chosen cities from db and maps the list to list of CityModels
        return cityDao.getAllLastChosenCities()
            .subscribeOn(schedulersIO)
            .map { mapper.fromEntityToCityModelList(it) }
    }

    override fun insertCityToLastChosenCitiesEntity(
        cityId: Int,
        cityList: List<CityModel>?
    ): Completable {
        return Completable.fromCallable {
            database.runInTransaction {
                val tableSize = cityDao.getTableSize()
                if (tableSize == MAX_TABLE_SIZE) cityDao.deleteExtraCity()
            }
            cityList?.filter { it.city_id == cityId }?.get(0)?.let {
                cityDao.insertCity(mapper.fromCityModelToEntity(it))
            }
        }
    }
}