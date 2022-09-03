package com.app.data.model.forecast


import com.app.data.model.current.Clouds
import com.app.data.model.current.Sys
import com.app.data.model.current.Weather
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