package com.app.feature.current

import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.model.current.CurrentWeather
import com.app.weather.model.forecast.Forecast
import com.app.weather.repository.WeatherRepository
import com.app.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _currentWeather = MutableLiveData<Resource<CurrentWeather>>(Resource.Initialize)
    val currentWeatherResponse: LiveData<Resource<CurrentWeather>> = _currentWeather

    private val _forecastWeather = MutableLiveData<Resource<Forecast>>(Resource.Initialize)
    val forecastWeatherResponse: LiveData<Resource<Forecast>> = _forecastWeather


    /**
     * Current weather
     *
     * @param lat
     * @param lon
     */
    fun currentWeather(
        lat: String,
        lon: String
    ) = viewModelScope.launch {
        _currentWeather.postValue(Resource.Loading)
        try {
            val response = repository.currentWeather(lat = lat, lon = lon)
            Log.d("tag", "getWeather Error: $response")

            if (response.isSuccessful) {
                _currentWeather.postValue(Resource.Success(response.body()!!))
            } else {
                _currentWeather.postValue(Resource.Failure("Api Failed"))
            }
        } catch (e: Exception) {
            _currentWeather.postValue(Resource.Failure(e.message.toString()))
            Log.d("tag", "getWeather Error: ${e.message}")
        }

    }

    fun forecastWeather(
        lat: String,
        lon: String
    ) = viewModelScope.launch {
        _forecastWeather.postValue(Resource.Loading)
        try {
            val response = repository.forecastWeather(lat = lat, lon = lon)
            Log.d("tag", "forecastWeather Error: $response")

            if (response.isSuccessful) {
                _forecastWeather.postValue(Resource.Success(response.body()!!))
            } else {
                _forecastWeather.postValue(Resource.Failure("Api Failed"))
            }
        } catch (e: Exception) {
            _forecastWeather.postValue(Resource.Failure(e.message.toString()))
            Log.d("tag", "forecastWeather Error: ${e.message}")
        }

    }

    /**
     * Is location enabled
     *
     * @param context
     * @return
     */
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


}