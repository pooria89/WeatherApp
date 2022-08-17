package com.app.weather.api

import com.app.weather.model.CurrentWeather
import com.app.weather.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Accept: application/json")
    @GET("weather")
    suspend fun currentWeather(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String="metric",
        @Query("lang") lang: String="fa",
    ): Response<CurrentWeather>

    @Headers("Accept: application/json")
    @GET("weather")
    suspend fun forecastWeather(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("lang") lang: String="fa",
        @Query("cnt") cnt: Int = 16,
        @Query("units") units: String="metric",
    ): Response<CurrentWeather>

}