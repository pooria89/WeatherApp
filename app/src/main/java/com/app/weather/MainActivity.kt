package com.app.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.weather.databinding.ActivityMainBinding
import com.app.weather.utils.Resource
import com.app.weather.utils.ext.fadeIn
import com.app.weather.utils.ext.hide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val permissionId: Int = 100
    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var adapter: WeatherAdapter

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
        viewModel.currentWeather(lat = "", lon = "51.399406")
        observer()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        Log.e("gpssss", "Latitude\n ${location.latitude}")
                        Log.e("gpssss", "Longitude\n${location.longitude}")
//                        Log.e("gpssss", "Country Name\n${list[0].countryName}")
//                        Log.e("gpssss", "Locality\n${list[0].locality}")
//                        Log.e("gpssss", "Address\n${list[0].getAddressLine(0)}")
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }


    private fun observer() = binding.apply {

        viewModel.currentWeatherResponse.observe(this@MainActivity) { currentWeather ->

            when (currentWeather) {
                is Resource.Loading -> showProgress()
                is Resource.Success -> {
                    Log.d("currentWeather", currentWeather.toString())
                    hideProgress()
                    setupRecyclerView(rvWeather)
                    txtDescription.text = currentWeather.data.weather?.firstOrNull()?.main
                    txtCity.text = currentWeather.data.name
                    txtTemp.text = currentWeather.data.main?.temp?.toInt().toString() + " °C"
                    txtMinTemp.text = currentWeather.data.main?.tempMin?.toInt().toString() + " °C"
                    txtMaxTemp.text = currentWeather.data.main?.tempMax?.toInt().toString() + " °C"
//                    txtTemp.text = currentWeather.data
//                    adapter.submitList(currentWeather.data.weather)
                }
                is Resource.Failure -> {
                    hideProgress()
                }
            }

        }

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        adapter = WeatherAdapter { }
        recyclerView.adapter = adapter
    }

    private fun showProgress() {
        binding.loading.root.fadeIn()
    }

    private fun hideProgress() {
        binding.loading.root.hide()
    }

}