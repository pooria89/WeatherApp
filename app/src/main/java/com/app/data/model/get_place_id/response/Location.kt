package com.app.data.model.get_place_id.response


import com.app.data.model.get_place_id.response.Locale

data class Location(
    val address: List<String?>?,
    val adminDistrict: List<String?>?,
    val adminDistrictCode: List<Any?>?,
    val city: List<String?>?,
    val country: List<String?>?,
    val countryCode: List<String?>?,
    val displayName: List<String?>?,
    val disputedArea: List<Boolean?>?,
    val disputedCountries: List<Any?>?,
    val disputedCountryCodes: List<Any?>?,
    val disputedCustomers: List<Any?>?,
    val disputedShowCountry: List<List<Boolean?>?>?,
    val ianaTimeZone: List<String?>?,
    val iataCode: List<String?>?,
    val icaoCode: List<String?>?,
    val latitude: List<Double?>?,
    val locId: List<String?>?,
    val locale: List<Locale?>?,
    val locationCategory: List<Any?>?,
    val longitude: List<Double?>?,
    val neighborhood: List<Any?>?,
    val placeId: List<String?>?,
    val postalCode: List<String?>?,
    val postalKey: List<String?>?,
    val pwsId: List<String?>?,
    val type: List<String?>?
)