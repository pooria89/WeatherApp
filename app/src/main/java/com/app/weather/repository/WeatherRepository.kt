package com.app.weather.repository

import com.app.weather.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) {

    /**
     * Current weather
     *
     * @param lat
     * @param lon
     */
    suspend fun currentWeather(
        lat: String,
        lon: String
    ) = apiService.currentWeather(
        lat = lat,
        lon = lon
    )

    /**
     * Forecast weather
     *
     * @param lat
     * @param lon
     */
    suspend fun forecastWeather(
        lat: String,
        lon: String
    ) = apiService.forecastWeather(
        lat = lat,
        lon = lon
    )

}