package com.app.data.api

import com.app.data.model.get_place_id.request.PlaceId
import com.app.data.model.get_place_id.response.PlaceIdResult
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

//    @Headers("Accept: application/json")
//    @GET("weather")
//    suspend fun currentWeather(
//        @Query("apikey") apikey: String = Constants.API_KEY,
//        @Query("lat") lat: String,
//        @Query("lon") lon: String,
//        @Query("units") units: String = "metric",
//        @Query("lang") lang: String = "fa",
//    ): Response<CurrentWeather>

    @Headers("Accept: application/json")
    @POST("redux-dal")
    suspend fun getPlaceId(
        @Body model: List<PlaceId>
    ): PlaceIdResult

}