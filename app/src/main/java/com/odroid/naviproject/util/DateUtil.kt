package com.odroid.naviproject.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getFormattedDate(fromDate: String, fromPattern: String, toPattern: String): String {
        val parser = SimpleDateFormat(fromPattern, Locale.ENGLISH)
        val formatter = SimpleDateFormat(toPattern, Locale.ENGLISH)
        return formatter.format(parser.parse(fromDate))
    }
}