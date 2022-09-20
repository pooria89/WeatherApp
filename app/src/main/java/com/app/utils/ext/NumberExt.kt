package com.app.utils.ext

import java.text.SimpleDateFormat
import java.util.*


const val YMD_FORMAT = "yyyy-MM-dd HH:mm:ss"

fun String.toTimestamp(dateFormat: String = YMD_FORMAT): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.toTimestamp(dateFormat: String = YMD_FORMAT): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return formatter.format(Date(this))
}