package com.example.data.implementationRepo

import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.utils.CityDataCache
import com.example.data.utils.getCurrentDateByTimezone
import com.example.domain.repositories.DeleteOldWeatherDataRepository

class DeleteOldWeatherDataRepoImpl(
    private val weatherDataDao: WeatherDataDao,
    private val timeZoneDao: TimezoneDao,
    private val cityDataCache: CityDataCache
) : DeleteOldWeatherDataRepository {

    override fun deleteOldWeatherDataFromEntity() {
        val currentDate =
            getCurrentDateByTimezone(timeZoneDao.getTimezoneByCityId(cityDataCache.loadCityId()))
        weatherDataDao.deleteOldWeatherData(currentDate)
    }
}