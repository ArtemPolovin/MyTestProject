
package com.example.data.utils

import android.content.Context
import com.example.data.R
import com.example.domain.models.CityModel
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class CityConverter(
    private val context: Context,
    private val gson: Gson
) {

    fun getCityModelByCityId(cityId: Int): CityModel {
        val cityModelList = getCityModelsListFromJson()
        return cityModelList.filter { it.city_id == cityId }[0] // the "cityId" comes from the same list from which the "cityModelList" consists, so the "cityModelList" necessarily contains the "cityId"
    }


    private fun fromSteamToString(): String { //The method parses json file with list of cities data to string
        val inputStream = context.resources.openRawResource(R.raw.cities_20000)
        val isReader = InputStreamReader(inputStream)
        val reader = BufferedReader(isReader)
        val strBuilder = StringBuilder()
        reader.use { it ->
            var str = it.readLine()
            while (str != null) {
                strBuilder.append(str)
                str = it.readLine()
            }
        }
        inputStream.close()
        isReader.close()
        return strBuilder.toString()
    }

    fun getCityModelsListFromJson(): Array<CityModel> {
        return gson.fromJson(fromSteamToString(), Array<CityModel>::class.java)
    }

}