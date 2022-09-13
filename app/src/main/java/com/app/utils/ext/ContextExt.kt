package com.app.utils.ext

import android.content.Context
import android.location.LocationManager


/**
 * Is location enabled
 *
 * @return
 */
fun Context.isGpsEnabled(): Boolean {
    val locationManager: LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )}