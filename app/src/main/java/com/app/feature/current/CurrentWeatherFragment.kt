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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.app.common.Resource
import com.app.data.model.current.CurrentWeather
import com.app.data.model.current.WeatherType
import com.app.data.model.get_place_id.response.GetSunV3LocationSearchUrlConfig
import com.app.utils.ext.*
import com.app.weather.databinding.FragmentCurrentWeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {


    private val viewModel: CurrentWeatherViewModel by viewModels()
    private lateinit var binding: FragmentCurrentWeatherBinding

    private lateinit var adapter: ForecastAdapter
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var latitude: String = ""
    private var longitude: String = ""

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

    private fun setupView() = binding.apply {
        lottieSunrise.setAnimation("sunrise.json")
        lottieSunset.setAnimation("sunset.json")
        lottieHumidity.setAnimation("humidity.json")
        setupRecyclerView(rvWeather)
    }

    private fun getCurrentLocation() {
        if (requireContext().isGpsEnabled()) {
            mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
                Log.e("gpssss", "Latitude\n ${location.latitude}")
                Log.e("gpssss", "Longitude\n${location.longitude}")
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                viewModel.getPlaceId(
                    geo = "${location.latitude}" + "," + "${location.longitude}"
                )
            }
        } else {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    private fun setupCurrentWeatherUI(currentWeather: CurrentWeather) = binding.apply {
        txtDegreeStatus.text = currentWeather.weather?.firstOrNull()?.description
        txtTemp.text = currentWeather.main?.temp.toString().showTemp()
        txtHumidity.text = currentWeather.main?.humidity.toString().showPercentage()
        txtSunrise.text = currentWeather.sys?.sunrise?.toLong()?.toTimestamp()?.toTime()
        txtSunset.text = currentWeather.sys?.sunset?.toLong()?.toTimestamp()?.toTime()

        when (currentWeather.weather?.firstOrNull()?.main) {
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

    private fun observe() {

        lifecycleScope.launchWhenCreated {
            viewModel.getPlaceId.collect {
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, "observe: $it")

                        it.data.dal?.getSunV3LocationSearchUrlConfig?.forEach { (key, value) ->
                            Log.d(TAG, "$key = $value")
                            viewModel.getCurrentWeather(
                                latitude = value.data?.location?.latitude?.firstOrNull().toString(),
                                longitude = value.data?.location?.longitude?.firstOrNull()
                                    .toString()
                            )
                            setupLocationUI(value)
                        }
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "مشکلی رخ داده است", Toast.LENGTH_SHORT)
                            .show()
                        hideProgress()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getCurrentWeather.collect {
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, "observe: $it")
                        setupCurrentWeatherUI(it.data)
                        viewModel.getForecastWeather(
                            latitude = latitude,
                            longitude = longitude
                        )
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "مشکلی رخ داده است", Toast.LENGTH_SHORT)
                            .show()
                        hideProgress()
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getForecastWeather.collect {
                when (it) {
                    is Resource.Loading -> hideProgress()
                    is Resource.Success -> {
                        binding.apply {
                            llHumidity.visibility = View.VISIBLE
                            llSunset.visibility = View.VISIBLE
                            llSunsrise.visibility = View.VISIBLE
                            txtForecast.visibility = View.VISIBLE
                        }
                        adapter.submitList(it.data.list)
                        Log.d(TAG, "observe: $it")
                        hideProgress()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(requireContext(), "مشکلی رخ داده است", Toast.LENGTH_SHORT)
                            .show()
                        hideProgress()
                    }
                }
            }

        }

    }

    private fun requestPermission() {
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    override fun onResume() {
        super.onResume()
        getCurrentLocation()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = ForecastAdapter { }
        recyclerView.adapter = adapter
    }

    private fun showProgress() {
        binding.loading.root.fadeIn()
    }

    private fun hideProgress() {
        binding.loading.root.hide()
    }

}