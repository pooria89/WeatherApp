package com.app.weather.model.forecast


import com.app.weather.model.current.Clouds
import com.app.weather.model.current.Sys
import com.app.weather.model.current.Weather
import com.google.gson.annotations.SerializedName

data class ListItem(
    val clouds: Clouds?,
    val dt: Long?,
    @SerializedName("dt_txt")
    val dtTxt: String?,
    val main: Main?,
    val pop: Double?,
    val sys: Sys?,
    val visibility: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?
)