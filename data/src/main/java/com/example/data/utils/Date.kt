package com.example.data.utils

import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_FORMAT_DATE = "yyyy-MM-dd"
const val CONVERTED_FORMAT_DATE = "EEEE, d"

fun parsingDate(dateString: String): String {  //The method converts date. For example from "8.16.2020" to "Sunday, 16"
    val requestFormat = SimpleDateFormat(REQUEST_FORMAT_DATE,Locale.ENGLISH)
    val date = requestFormat.parse(dateString)
    return SimpleDateFormat(CONVERTED_FORMAT_DATE,Locale.ENGLISH).format(date ?: "")
}

fun getCurrentDateByTimezone(timezone: String) : String{
    val timeZone = TimeZone.getTimeZone(timezone)
    val requiredFormat = SimpleDateFormat(REQUEST_FORMAT_DATE, Locale.ENGLISH)
    requiredFormat.timeZone = timeZone
    return requiredFormat.format(Date())
}



fun getDateList(days: Int,timezone: String): List<String> { //The method gets current date by timezone and creates list of next dates depending on the number of received days
    val currentDate = getCurrentDateByTimezone(timezone)
    val list = mutableListOf<String>()

    val requestFormat = SimpleDateFormat(REQUEST_FORMAT_DATE,Locale.ENGLISH)
    val date = requestFormat.parse(currentDate)
    val calendar = GregorianCalendar()
    date?.let { calendar.time = it }


    for (i in 1 until days) {  // The loop is one pass less because the date of the current day is not included
        calendar.add(Calendar.DATE,1)
        val result = requestFormat.format(calendar.time)
        list.add(result)
    }
    return list
}