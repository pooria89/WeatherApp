package com.app.data.repository

import com.app.data.api.ApiService
import com.app.data.model.get_place_id.request.Geo
import com.app.data.model.get_place_id.request.PlaceId
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) {

    /**
     * Get place id
     *
     * @param geo
     */
    suspend fun getPlaceId(
        geo: String
    ) = apiService.getPlaceId(
        listOf(
            PlaceId(
                params = Geo(query = geo)
            )
        )
    )


    /**
     * Get current weather
     *
     * @param latitude
     * @param longitude
     */
    suspend fun getCurrentWeather(
        latitude: String, longitude: String
    ) = apiService.currentWeather(
        lat = latitude,
        lon = longitude
    )

    suspend fun getForecastWeather(
        latitude: String, longitude: String
    ) = apiService.forecastWeather(
        latitude = latitude,
        longitude = longitude
    )
}