package com.example.data.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun parsingDate(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse(date, formatter)
    return dateTime.format(DateTimeFormatter.ofPattern("EEEE, d"))
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDateByTimezone(timezone: String) : String{
    val timeZone = TimeZone.getTimeZone(timezone)
    val requiredFormat = SimpleDateFormat("yyyy-MM-dd")
    requiredFormat.timeZone = timeZone
    return requiredFormat.format(Date())
}

@RequiresApi(Build.VERSION_CODES.O)
 fun getDateList(days: Int,timezone: String): List<String> {
    val currentDate = getCurrentDateByTimezone(timezone)
    val list = mutableListOf<String>()
    var localDate = LocalDate.parse(currentDate)

    for (i in 1 until days) {                // The loop is one pass less because the date of the current day is not included
        localDate = localDate.plusDays(1)
        list.add(localDate.toString())
    }
    return list
}