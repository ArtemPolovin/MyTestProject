package com.example.mytestproject.data.repository

import com.example.mytestproject.data.network.response.ResponseTomorrowWeatherData
import io.reactivex.rxjava3.core.Single

interface TomorrowWeatherRepository {
    fun getTomorrowWeatherData(
        key: String,
        city: String,
        days: Int,
        degreeType: String
    ): Single<ResponseTomorrowWeatherData>
}