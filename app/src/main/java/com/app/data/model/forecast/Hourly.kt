package com.app.data.model.forecast

data class Hourly(
    val relativehumidity_2m: List<Double>,
    val temperature_2m: List<Double>,
    val time: List<String>,
    val windspeed_10m: List<Double>
)