package com.app.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.model.Weather
import com.app.weather.repository.WeatherRepository
import com.app.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weather = MutableLiveData<Resource<Weather?>>(Resource.Initialize)
    val weatherResponse: LiveData<Resource<Weather?>> = _weather

    fun getWeather() = viewModelScope.launch {
        _weather.postValue(Resource.Loading)
        repository.getWeather().let { response ->
            if (response.isSuccessful) {
                _weather.postValue(Resource.Success(response.body()))
            } else {
                _weather.postValue(Resource.Failure("Api Failed"))
                Log.d("tag", "getWeather Error: ${response.code()}")
            }
        }
    }

}