@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.data.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun parsingDate(date: String): String {
  /*  val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse(date, formatter)
    return dateTime.format(DateTimeFormatter.ofPattern("EEEE, d"))*/
    val requestFormat = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
    val d = requestFormat.parse(date)
    return SimpleDateFormat("EEEE, d").format(d)
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDateByTimezone(timezone: String) : String{
    val timeZone = TimeZone.getTimeZone(timezone)
    val requiredFormat = SimpleDateFormat("yyyy-MM-dd")
    requiredFormat.timeZone = timeZone
    return requiredFormat.format(Date())
}

@SuppressLint("SimpleDateFormat")
 fun getDateList(days: Int,timezone: String): List<String> {
    val currentDate = getCurrentDateByTimezone(timezone)
    val list = mutableListOf<String>()

    val requestFormat = SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH)
    val d = requestFormat.parse(currentDate)
    val calendar = GregorianCalendar()
    calendar.time = d

    for (i in 1 until days) {  // The loop is one pass less because the date of the current day is not included
        calendar.add(Calendar.DATE,1)
        val result = requestFormat.format(calendar.time)
        list.add(result)
    }

    return list
}
