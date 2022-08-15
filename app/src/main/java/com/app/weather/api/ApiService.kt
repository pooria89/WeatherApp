package com.app.weather.api

import com.app.weather.model.Weather
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("weather/Tehran")
    suspend fun getWeather(): Response<Weather>

}