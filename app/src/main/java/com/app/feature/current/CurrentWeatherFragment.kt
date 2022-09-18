package com.app.feature.current

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.app.common.Resource
import com.app.data.model.get_place_id.response.GetSunV3LocationSearchUrlConfig
import com.app.utils.ext.fadeIn
import com.app.utils.ext.hide
import com.app.utils.ext.isGpsEnabled
import com.app.weather.databinding.FragmentCurrentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {


    private val viewModel: CurrentWeatherViewModel by viewModels()
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
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setupView()
        observe()
        requestPermission()
        getCurrentLocation()
    }

    private fun setupView() {

    }

    private fun observe() {

        lifecycleScope.launchWhenCreated {
            viewModel.getPlaceId.collect {
                when (it) {
                    is Resource.Loading -> hideProgress()
                    is Resource.Success -> {
                        hideProgress()
                        Log.d(TAG, "observe: $it")

                        it.data.dal?.getSunV3LocationSearchUrlConfig?.forEach { (key, value) ->
                            Log.d(TAG, "$key = $value")
                            viewModel.getCurrentWeather(
                                latitude = value.data?.location?.latitude?.firstOrNull().toString(),
                                longitude = value.data?.location?.longitude?.firstOrNull().toString()
                            )
                            setupLocationUI(value)
                        }
                    }
                    is Resource.Failure -> {
                        hideProgress()
                    }
                }
            }

            viewModel.getCurrentWeather.collect {
                when (it) {
                    is Resource.Loading -> hideProgress()
                    is Resource.Success -> {
                        hideProgress()
                        Log.d(TAG, "observe: $it")
                    }
                    is Resource.Failure -> {
                        hideProgress()
                    }
                }
            }
        }

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

    private fun setupLocationUI(value: GetSunV3LocationSearchUrlConfig) = binding.apply {

        val location = value.data?.location

        val placeId = location?.placeId?.firstOrNull() // "sdfsdfjsdfgbfdg"
        val latitude = location?.latitude?.firstOrNull() // ۲۳۹۴۸۲۳۹۰۴۸
        val longitude = location?.longitude?.firstOrNull() //۳۲۹۴۸۳۴
        val city = location?.city?.firstOrNull() //تهران
        val country = location?.country?.firstOrNull() // ایران
        val displayName = location?.displayName?.firstOrNull() // میدان راه اهن
        val ianaTimeZone = location?.ianaTimeZone?.firstOrNull() // اسیا / تهران
        val locale = location?.locale?.firstOrNull()?.locale3 // میدان بهادری

        txtCountry.text = country.toString()
        txtCity.text = displayName.toString()
        txtDate.text = ianaTimeZone.toString()

    }

    private fun requestPermission() {
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun getCurrentLocation() {
        if (requireContext().isGpsEnabled()) {
            mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Log.e("gpssss", "Latitude\n ${location.latitude}")
                Log.e("gpssss", "Longitude\n${location.longitude}")
                viewModel.getPlaceId(
                    geo = "${location.latitude}" + "," + "${location.longitude}"
                )
            }
        } else {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
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