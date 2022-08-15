package com.app.weather.repository

import com.app.weather.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService) {

    suspend fun getWeather() = apiService.getWeather()
}