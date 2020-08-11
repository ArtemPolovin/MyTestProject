package com.example.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalStdlibApi
fun parsingDate(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val dateTime = LocalDate.parse(date, formatter)
    return dateTime.format(DateTimeFormatter.ofPattern("EEEE, d"))
}