@file:Suppress("NAME_SHADOWING")

package com.example.data.utils

import android.content.Context
import com.example.data.R
import com.example.domain.models.CityModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

fun getCityModelByCityName(context: Context, cityName: String): CityModel {
    var cityModel = CityModel(0, "","")
    val cityModelList = getCityModelsListFromJson(context)
    cityModelList.forEach {
        if (it.city_name == cityName) {
            cityModel = it
            return@forEach
        }
    }
    return cityModel
}


private fun fromSteamToString(context: Context): String { //The method parses json file with list of cities data to string
    val inputStream = context.resources.openRawResource(R.raw.cities_20000)
    val isReader = InputStreamReader(inputStream)
    val reader = BufferedReader(isReader)
    val strBuilder = StringBuilder()
    reader.use { reader ->
        var str = reader.readLine()
        while (str != null) {
            strBuilder.append(str)
            str = reader.readLine()
        }
    }
    inputStream.close()
    return strBuilder.toString()
}

 fun getCityModelsListFromJson(context: Context): Array<CityModel> {
    val gson = Gson()
    return gson.fromJson(fromSteamToString(context), Array<CityModel>::class.java)
}