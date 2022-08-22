package com.app.weather.model.forecast


import com.google.gson.annotations.SerializedName

data class Forecast(
    val city: City?,
    val cnt: Int?,
    val cod: String?,
    val list: List<ListItem>?,
    val message: Int?
)