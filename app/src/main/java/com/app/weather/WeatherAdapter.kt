package com.app.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.app.common.BaseRecyclerViewAdapter
import com.app.weather.databinding.RecyclerItemBinding
import com.app.weather.model.current.Weather

class WeatherAdapter(
    private val listener: ((Weather) -> Unit)
) : BaseRecyclerViewAdapter<Weather, WeatherAdapter.VH>(COMPARATOR) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) =
        VH(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))

    inner class VH(
        private val binding: RecyclerItemBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(item: Weather) {
            binding.apply {
//                txtTemperature.text = item.main
//                txtWind.text = item.name
//                txtDay.text = item.name
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Weather>() {
            override fun areItemsTheSame(
                oldItem: Weather,
                newItem: Weather
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Weather,
                newItem: Weather
            ) = oldItem == newItem
        }
    }


}