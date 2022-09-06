package com.app.feature.current

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.data.model.current.WeatherType
import com.app.data.utils.ext.isLocationEnabled
import com.app.utils.Resource
import com.app.utils.ext.fadeIn
import com.app.utils.ext.hide
import com.app.weather.databinding.ActivityCurrentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherActivity : AppCompatActivity() {

    private lateinit var adapter: ForecastWeatherAdapter
    private lateinit var binding: ActivityCurrentWeatherBinding
    private val viewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var mFusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        observer()
        getLocation()

    }


    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (isLocationEnabled()) {
            mFusedLocationClient.lastLocation.addOnSuccessListener(this) { task ->
                val location: Location? = task
                Log.e("gpssss", "Latitude\n ${location?.latitude}")
                Log.e("gpssss", "Longitude\n${location?.longitude}")
//                    if (location != null) {
//                        Log.e("gpssss", "Latitude\n ${location.latitude}")
//                        Log.e("gpssss", "Longitude\n${location.longitude}")
//                        viewModel.currentWeather(
//                            lat = location.latitude.toString(),
//                            lon = location.longitude.toString()
//                        )
//
//                    }
            }
        } else {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

    }

    private fun observer() = binding.apply {

        viewModel.currentWeatherResponse.observe(this@CurrentWeatherActivity) { currentWeather ->

            when (currentWeather) {
                is Resource.Loading -> showProgress()
                is Resource.Success -> {
                    viewModel.forecastWeather(
                        lat = currentWeather.data.coord?.lat.toString(),
                        lon = currentWeather.data.coord?.lon.toString()
                    )
                    Log.d("currentWeather", currentWeather.toString())
                    hideProgress()
                    setupRecyclerView(rvWeather)
                    txtDescription.text = currentWeather.data.weather?.firstOrNull()?.description
                    txtCity.text = currentWeather.data.name
                    txtTemp.text = currentWeather.data.main?.temp?.toInt().toString() + " °C"
                    txtMinTemp.text = currentWeather.data.main?.tempMin?.toInt().toString() + " °C"
                    txtMaxTemp.text = currentWeather.data.main?.tempMax?.toInt().toString() + " °C"
                    when (currentWeather.data.weather?.firstOrNull()?.main) {
                        WeatherType.WEATHER_SUNNY  -> {
                            animationWeather.setAnimation("weather_sunny.json")
                        }
                        WeatherType.WEATHER_CLOUDY -> {
                            animationWeather.setAnimation("weather_cloudy.json")
                        }
                        WeatherType.WEATHER_RAINY  -> {
                            animationWeather.setAnimation("weather_rainy.json")
                        }
                        WeatherType.WEATHER_CLEAR  -> {
                            animationWeather.setAnimation("weather_clear.json")
                        }
                        WeatherType.WEATHER_SNOW   -> {
                            animationWeather.setAnimation("weather_snow.json")
                        }
                    }
                }
                is Resource.Failure -> {
                    hideProgress()
                }
            }

        }

        viewModel.forecastWeatherResponse.observe(this@CurrentWeatherActivity) { forecastWeather ->

            when (forecastWeather) {
                is Resource.Loading -> showProgress()
                is Resource.Success -> {
                    Log.d("forecastWeather", forecastWeather.toString())
                    hideProgress()
                    setupRecyclerView(rvWeather)
                    adapter.submitList(forecastWeather.data.list)
                }
                is Resource.Failure -> {
                    hideProgress()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = ForecastWeatherAdapter { }
        recyclerView.adapter = adapter
    }

    private fun showProgress() {
        binding.loading.root.fadeIn()
    }

    private fun hideProgress() {
        binding.loading.root.hide()
    }

}