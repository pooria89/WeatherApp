package com.app.feature.current

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.utils.ext.fadeIn
import com.app.utils.ext.hide
import com.app.weather.databinding.FragmentCurrentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {


    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var binding: FragmentCurrentWeatherBinding

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)   -> {}
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {}
            else                                                                        -> {}
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        observe()
        requestPermission()
//        getCurrentLocation()
    }

    private fun setupView() {

    }

    private fun observe() {
//        viewModel.currentWeatherResponse.observe(this@MainActivity) { currentWeather ->
//
//            when (currentWeather) {
//                is Resource.Loading -> showProgress()
//                is Resource.Success -> {
//                    viewModel.forecastWeather(
//                        lat = currentWeather.data.coord?.lat.toString(),
//                        lon = currentWeather.data.coord?.lon.toString()
//                    )
//                    Log.d("currentWeather", currentWeather.toString())
//                    hideProgress()
//                    setupRecyclerView(rvWeather)
//                    txtDescription.text = currentWeather.data.weather?.firstOrNull()?.description
//                    txtCity.text = currentWeather.data.name
//                    txtTemp.text = currentWeather.data.main?.temp?.toInt().toString() + " °C"
//                    txtMinTemp.text = currentWeather.data.main?.tempMin?.toInt().toString() + " °C"
//                    txtMaxTemp.text = currentWeather.data.main?.tempMax?.toInt().toString() + " °C"
//                    when (currentWeather.data.weather?.firstOrNull()?.main) {
//                        WeatherType.WEATHER_SUNNY  -> {
//                            animationWeather.setAnimation("weather_sunny.json")
//                        }
//                        WeatherType.WEATHER_CLOUDY -> {
//                            animationWeather.setAnimation("weather_cloudy.json")
//                        }
//                        WeatherType.WEATHER_RAINY  -> {
//                            animationWeather.setAnimation("weather_rainy.json")
//                        }
//                        WeatherType.WEATHER_CLEAR  -> {
//                            animationWeather.setAnimation("weather_clear.json")
//                        }
//                        WeatherType.WEATHER_SNOW   -> {
//                            animationWeather.setAnimation("weather_snow.json")
//                        }
//                    }
//                }
//                is Resource.Failure -> {
//                    hideProgress()
//                }
//            }
//
//        }

    }

    private fun requestPermission() {
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

//    private fun getCurrentLocation() {
//        if (isLocationEnabled()) {
//
//            mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
//                Log.e("gpssss", "Latitude\n ${location.latitude}")
//                Log.e("gpssss", "Longitude\n${location.longitude}")
////                viewModel.currentWeather(
////                    lat = location.latitude.toString(),
////                    lon = location.longitude.toString()
////                )
//            }
//        } else {
//            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//            startActivity(intent)
//        }
//    }

    override fun onResume() {
        super.onResume()
//        getCurrentLocation()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
//        adapter = ForecastWeatherAdapter { }
//        recyclerView.adapter = adapter
    }

    private fun showProgress() {
        binding.loading.root.fadeIn()
    }

    private fun hideProgress() {
        binding.loading.root.hide()
    }

}