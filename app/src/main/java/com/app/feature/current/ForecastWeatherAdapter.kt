package com.app.feature.current

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.app.common.BaseRecyclerViewAdapter
import com.app.weather.databinding.ForecastItemBinding
import com.app.weather.model.current.WeatherType
import com.app.weather.model.forecast.ListItem
import com.app.weather.utils.ext.toPersianDateTime

class ForecastWeatherAdapter(
    private val listener: ((ListItem) -> Unit)
) : BaseRecyclerViewAdapter<ListItem, ForecastWeatherAdapter.VH>(COMPARATOR) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ForecastItemBinding.inflate(LayoutInflater.from(parent.context)))

    inner class VH(
        private val binding: ForecastItemBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(item: ListItem) {
            binding.apply {
                txtTemperature.text = item.main?.temp?.toInt().toString() + " °C"
                txtDescription.text = item.weather?.firstOrNull()?.description
                txtWind.text = item.wind?.speed.toString()
                txtTime.text = item.dtTxt?.toPersianDateTime()

                when (item.weather?.firstOrNull()?.main) {
                    WeatherType.WEATHER_SUNNY  -> {
                        imgItemList.setAnimation("weather_sunny.json")
                    }
                    WeatherType.WEATHER_CLOUDY -> {
                        imgItemList.setAnimation("weather_cloudy.json")
                    }
                    WeatherType.WEATHER_RAINY  -> {
                        imgItemList.setAnimation("weather_rainy.json")
                    }
                    WeatherType.WEATHER_CLEAR  -> {
                        imgItemList.setAnimation("weather_clear.json")
                    }
                    WeatherType.WEATHER_SNOW   -> {
                        imgItemList.setAnimation("weather_snow.json")
                    }
                }
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(
                oldItem: ListItem,
                newItem: ListItem
            ) = oldItem.dt == newItem.dt

            override fun areContentsTheSame(
                oldItem: ListItem,
                newItem: ListItem
            ) = oldItem == newItem
        }
    }


}