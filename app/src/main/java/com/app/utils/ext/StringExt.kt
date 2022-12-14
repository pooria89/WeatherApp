package com.app.utils.ext

import saman.zamani.persiandate.PersianDate
import java.text.SimpleDateFormat
import java.util.*

/**
 * Show temp
 *
 * @param temp
 */
fun String.showTemp() = "$this °C"

/**
 * Show at
 *
 */
fun String.showAt() = "  اتمسفر$this"

fun String.showPercentage() = "$this %"

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
 * To time
 *
 * @param hasSecond
 * @return
 */
fun String.toTime(hasSecond: Boolean = true): String {
    val date = PersianDate(getDate(this))
    val second = if (date.second < 10) "0${date.second}" else date.second
    if (hasSecond) return "${date.hour}:${date.minute}:$second"
    return "${date.hour}:${date.minute}"
}

fun String.toPersianDateTime(): String {
    val date = PersianDate(getDate(this))
    return "${date.dayName()} ${date.shDay} ${date.monthName()}، ساعت ${date.hour}:${date.minute}"
}

fun String.toPersianDate(): String {
    val date = PersianDate(getDate(this))
    return "${date.dayName()} ${date.shDay} ${date.monthName()}"
}

fun String.toNumericPersianDate(): String {
    val date = PersianDate(getDate(this))
    val day = if (date.shDay < 10) "0${date.shDay}" else date.shDay
    val month = if (date.shMonth < 10) "0${date.shMonth}" else date.shMonth
    return "${date.shYear}/$month/$day"
}