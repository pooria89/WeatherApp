package com.app.feature.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.common.Resource
import com.app.data.model.get_place_id.response.PlaceIdResult
import com.app.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getPlaceId(geo: String) = viewModelScope.launch {
        _getPlaceId.emit(Resource.Loading)
        try {
            val response = repository.getPlaceId(geo=geo)
            _getPlaceId.emit(Resource.Success(response))
            _getPlaceId.emit(
                Resource.Failure(
                    message = response.dal?.getSunV3LocationSearchUrlConfig.toString()
                )
            )
        } catch (e: Exception) {
            _getPlaceId.emit(Resource.Failure(message = e.message.toString()))
        }
    }


}