package com.app.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.airbnb.lottie.LottieAnimationView
import com.app.common.BaseRecyclerViewAdapter
import com.app.weather.databinding.RecyclerItemBinding
import com.app.weather.model.current.WeatherType
import com.app.weather.model.forecast.ListItem

class WeatherAdapter(
    private val listener: ((ListItem) -> Unit)
) : BaseRecyclerViewAdapter<ListItem, WeatherAdapter.VH>(COMPARATOR) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) =
        VH(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))

    inner class VH(
        private val binding: RecyclerItemBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(item: ListItem) {
            binding.apply {
                txtTemperature.text = item.main?.temp.toString()
                txtDescription.text = item.weather?.firstOrNull()?.main
                txtWind.text = item.wind?.speed.toString()
                txtDescMore.text = item.weather?.firstOrNull()?.description.toString()
                when (item.weather?.firstOrNull()?.main) {
                    WeatherType.WEATHER_SUNNY  -> {imgItemList.playAnimation()}
                    WeatherType.WEATHER_CLOUDY -> {}
                    WeatherType.WEATHER_RAINY  -> {}
                    WeatherType.WEATHER_CLEAR  -> {}
                    WeatherType.WEATHER_SNOW   -> {}
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