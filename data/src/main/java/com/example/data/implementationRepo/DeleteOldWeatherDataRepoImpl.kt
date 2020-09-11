package com.example.data.implementationRepo

import android.util.Log
import com.example.data.db.dao.TimezoneDao
import com.example.data.db.dao.WeatherDataDao
import com.example.data.utils.getCurrentDateByTimezone
import com.example.domain.repositories.DeleteOldWeatherDataRepository

class DeleteOldWeatherDataRepoImpl(
    private val weatherDataDao: WeatherDataDao,
    private val timeZoneDao: TimezoneDao
): DeleteOldWeatherDataRepository {

    override fun deleteOldWeatherDataFromEntity(cityId: Int) {
        val currentDate = getCurrentDateByTimezone(timeZoneDao.getTimezoneByCityId(cityId))
        weatherDataDao.deleteOldWeatherData(currentDate)
    }
}