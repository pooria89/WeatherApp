package com.app.weather

import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.app.weather.databinding.ActivityMainBinding
import com.app.weather.utils.Resource
import com.app.weather.utils.ext.fadeIn
import com.app.weather.utils.ext.hide
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        viewModel.getWeather()
        viewModel.currentWeather(lat = "35.6811336", lon = "51.399406")
        observer()

        binding.apply {

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
                    txtMinTemp.text = currentWeather.data.main?.tempMin?.toInt().toString()+ " °C"
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