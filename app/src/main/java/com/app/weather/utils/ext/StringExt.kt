package com.app.weather.utils.ext

import com.app.weather.utils.Constants.YMD_FORMAT
import saman.zamani.persiandate.PersianDate
import java.text.SimpleDateFormat
import java.util.*


/**
 * To millis
 *
 * @return
 */
fun String.toMillis(): Long {
    val format = if (this.contains("T")) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    } else {
        SimpleDateFormat(YMD_FORMAT, Locale.getDefault())
    }
    return try {
        format.parse(this)?.time ?: 0
    } catch (e: Exception) {
        0
    }
}

/**
 * Get date
 *
 * @param timestamp
 */
private fun getDate(timestamp: String) = Date(timestamp.toMillis())

/**
 * To persian date time
 *
 * @return
 */
fun String.toPersianDateTime(): String {
    val date = PersianDate(getDate(this))
    return "${date.dayName()} ${date.shDay} ${date.monthName()}، ساعت ${date.hour}:${date.minute}"
}