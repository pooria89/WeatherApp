package com.app.utils.ext

fun List<*>.isValidPosition(position: Int): Boolean {
    return if (isNotEmpty()) position in 0 until size else position >= 0
}