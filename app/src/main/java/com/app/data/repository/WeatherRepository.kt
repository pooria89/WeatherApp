package com.app.data.repository

import com.app.data.api.ApiService
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