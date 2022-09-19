package com.app.feature.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.common.Resource
import com.app.data.model.current.CurrentWeather
import com.app.data.model.forecast.ForecastWeather
import com.app.data.model.get_place_id.response.PlaceIdResult
import com.app.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {


    /**
     * _get place id
     */
    private val _getPlaceId = MutableStateFlow<Resource<PlaceIdResult>>(Resource.Initialize)
    val getPlaceId: Flow<Resource<PlaceIdResult>> = _getPlaceId

    fun getPlaceId(geo: String) = viewModelScope.launch() {
        _getPlaceId.emit(Resource.Loading)
        try {
            val response = repository.getPlaceId(geo = geo)
            _getPlaceId.emit(Resource.Success(response))
        } catch (e: Exception) {
            _getPlaceId.emit(Resource.Failure(message = e.message.toString()))
        }
    }


    private val _getCurrentWeather = MutableStateFlow<Resource<CurrentWeather>>(Resource.Initialize)
    val getCurrentWeather: Flow<Resource<CurrentWeather>> = _getCurrentWeather

    fun getCurrentWeather(latitude: String, longitude: String) = viewModelScope.launch() {
        _getCurrentWeather.emit(Resource.Loading)
        try {
            val response = repository.getCurrentWeather(
                latitude = latitude,
                longitude = longitude
            )
            _getCurrentWeather.emit(Resource.Success(response))
        } catch (e: Exception) {
            _getCurrentWeather.emit(Resource.Failure(message = e.message.toString()))
        }
    }

    private val _getForecastWeather =
        MutableStateFlow<Resource<ForecastWeather>>(Resource.Initialize)
    val getForecastWeather: Flow<Resource<ForecastWeather>> = _getForecastWeather

    fun getForecastWeather(latitude: String, longitude: String) = viewModelScope.launch() {
        _getForecastWeather.emit(Resource.Loading)
        try {
            val response = repository.getForecastWeather(
                latitude = latitude,
                longitude = longitude
            )
            _getForecastWeather.emit(Resource.Success(response))
        } catch (e: Exception) {
            _getForecastWeather.emit(Resource.Failure(message = e.message.toString()))
        }
    }
}