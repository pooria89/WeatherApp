package com.app.data.model.current

import com.app.data.model.forecast.Main


data class CurrentWeather(
    val base: String?,
    val clouds: Clouds?,
    val cod: Int?,
    val coord: Coord?,
    val dt: Int?,
    val id: Int?,
    val main: Main?,
    val name: String?,
    val sys: Sys?,
    val timezone: Int?,
    val visibility: Int?,
    val weather: List<Weather?>?,
    val wind: Wind?
)

object WeatherType {
    const val WEATHER_SUNNY = "sunny"
    const val WEATHER_CLOUDY = "Clouds"
    const val WEATHER_CLEAR = "Clear"
    const val WEATHER_RAINY = "Rain"
    const val WEATHER_SNOW = "Snow"
//    const val WEATHER_STORM = ""
}