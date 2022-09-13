package com.app.data.repository

import com.app.data.api.ApiService
import com.app.data.model.get_place_id.request.Geo
import com.app.data.model.get_place_id.request.PlaceId
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun getPlaceId(
        geo: String
    ) = apiService.getPlaceId(
        listOf(
            PlaceId(
                params = Geo(query = geo)
            )
        )
    )

}