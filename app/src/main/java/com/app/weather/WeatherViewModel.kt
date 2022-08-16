package com.app.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.model.CurrentWeather
import com.app.weather.repository.WeatherRepository
import com.app.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _currentWeather = MutableLiveData<Resource<CurrentWeather>>(Resource.Initialize)
    val currentWeatherResponse: LiveData<Resource<CurrentWeather>> = _currentWeather

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

    fun trimTrailingZeros(number: String): String? {
        return if (!number.contains(".")) {
            number
        } else number.replace("\\.?0*$".toRegex(), "")
    }


}