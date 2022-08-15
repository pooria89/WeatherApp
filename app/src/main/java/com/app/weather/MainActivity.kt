package com.app.weather

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
        viewModel.getWeather()
        observer()

    }

    private fun observer() = binding.apply {
        viewModel.weatherResponse.observe(this@MainActivity) { weather ->

            when (weather) {
                is Resource.Loading -> showProgress()
                is Resource.Success -> {
                    hideProgress()
                    Log.d("weather", weather.toString())
                    setupRecyclerView(rvWeather)
                    textDescription.text = weather.data?.description
                    txtTemp.text = weather.data?.forecast?.get(0)?.temperature
                    adapter.submitList(weather.data?.forecast)
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