package com.example.data.implementationRepo

import android.util.Log
import com.example.data.db.dao.CityDao
import com.example.data.mappers.LastChosenCitiesEntityMapper
import com.example.data.utils.MAX_TABLE_SIZE
import com.example.domain.models.CityModel
import com.example.domain.repositories.LastChosenCitiesRepo
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LastChosenCitiesRepoImpl(
    private val cityDao: CityDao,
    private val mapper: LastChosenCitiesEntityMapper
) : LastChosenCitiesRepo {

    override fun getLastChosenCities(): Single<List<CityModel>> {//This method gets list of last ten chosen cities from db and maps the list to list of CityModels
        return cityDao.getAllLastChosenCities()
            .subscribeOn(Schedulers.io())
            .map { mapper.fromEntityToCityModelList(it) }
    }

    override fun insertCityToLastChosenCitiesEntity(cityId: Int, cityList: List<CityModel>?): Disposable {

        return Single.just(cityId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { city_id ->
                    val tableSize = cityDao.getTableSize()

                    if (tableSize == MAX_TABLE_SIZE) cityDao.deleteExtraCity()

                    cityList?.filter { it.city_id == city_id }?.get(0)?.let {
                        cityDao.insertCity(mapper.fromCityModelToEntity(it))
                    }
                },
                {
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }
}