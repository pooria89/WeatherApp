package com.app.data.model.forecast


data class Forecast(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ListItem>?,
    val message: Int?
)