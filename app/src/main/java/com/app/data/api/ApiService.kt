package com.app.data.api

import com.app.common.Constants
import com.app.data.model.current.CurrentWeather
import com.app.data.model.get_place_id.request.PlaceId
import com.app.data.model.get_place_id.response.PlaceIdResult
import retrofit2.http.*

interface ApiService {


    /**
     * Get place id
     *
     * @param model
     * @return
     */
    @Headers("Accept: application/json")
    @POST("redux-dal")
    suspend fun getPlaceId(
        @Body model: List<PlaceId>
    ): PlaceIdResult

    /**
     * Current weather
     *
     * @param appid
     * @param lat
     * @param lon
     * @param units
     * @param lang
     * @return
     */
    @Headers("Accept: application/json")
    @GET("${Constants.BASE_URL1}weather")
    suspend fun currentWeather(
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fa",
    ): CurrentWeather
}