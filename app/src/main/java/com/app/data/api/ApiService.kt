package com.app.data.api

import com.app.data.model.current.CurrentWeather
import com.app.data.model.forecast.Forecast
import com.app.utils.Constants
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
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fa",
    ): Response<CurrentWeather>

    @Headers("Accept: application/json")
    @GET("forecast")
    suspend fun forecastWeather(
        @Query("apikey") apikey: String = Constants.API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fa",
    ): Response<Forecast>

}