package com.app.data.model.get_place_id.request

import com.app.data.model.get_place_id.request.Geo

data class PlaceId(
    val name: String = "getSunV3LocationSearchUrlConfig",
    val params: Geo,
)