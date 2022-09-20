package com.app.data.model.forecast


data class ForecastWeather(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ListForecast>?,
    val message: Int?
)