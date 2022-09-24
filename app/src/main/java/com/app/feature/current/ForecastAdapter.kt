package com.app.feature.current

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.app.common.BaseRecyclerViewAdapter
import com.app.data.model.current.WeatherType
import com.app.data.model.forecast.ListForecast
import com.app.utils.ext.toPersianDate
import com.app.utils.ext.toPersianDateTime
import com.app.utils.ext.toTime
import com.app.utils.ext.toTimestamp
import com.app.weather.databinding.ForecastItemBinding

class ForecastAdapter(
    private val listener: ((ListForecast) -> Unit)
) : BaseRecyclerViewAdapter<ListForecast, ForecastAdapter.VH>(COMPARATOR) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ForecastItemBinding.inflate(LayoutInflater.from(parent.context)))

    inner class VH(
        private val binding: ForecastItemBinding
    ) : BaseViewHolder(binding.root) {

        init {
//            binding.btnUnblock.setOnClickListener {
//                listener.invoke(
//                    getItem(bindingAdapterPosition)
//                )
//            }
        }

        override fun bind(item: ListForecast) {
            binding.apply {
//                tvUserUsername.text = item.username
//                tvUserFullName.text = item.full_name
//                ivUserAvatar.load(item.profile_pic_url)
                txtDate.text = item.dtTxt?.toPersianDate()
                txtTime.text = item.dtTxt?.toPersianDateTime()?.substringAfter("ساعت")

                txtDegree.text = item.main?.temp.toString()
                when (item.weather?.firstOrNull()?.main) {
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
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<ListForecast>() {
            override fun areItemsTheSame(
                oldItem: ListForecast,
                newItem: ListForecast
            ) = oldItem.main == newItem.main

            override fun areContentsTheSame(
                oldItem: ListForecast,
                newItem: ListForecast
            ) = oldItem == newItem
        }
    }


}