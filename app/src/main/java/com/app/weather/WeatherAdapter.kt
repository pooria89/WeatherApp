package com.app.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.app.common.BaseRecyclerViewAdapter
import com.app.weather.databinding.RecyclerItemBinding
import com.app.weather.model.Forecast

class WeatherAdapter(
    private val listener: ((Forecast) -> Unit)
) : BaseRecyclerViewAdapter<Forecast, WeatherAdapter.VH>(COMPARATOR) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int) =
        VH(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))

    inner class VH(
        private val binding: RecyclerItemBinding
    ) : BaseViewHolder(binding.root) {

        override fun bind(item: Forecast) {
            binding.apply {
                txtTemperature.text = item.temperature
                txtWind.text = item.wind
                txtDay.text = item.day
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(
                oldItem: Forecast,
                newItem: Forecast
            ) = oldItem.day == newItem.day

            override fun areContentsTheSame(
                oldItem: Forecast,
                newItem: Forecast
            ) = oldItem == newItem
        }
    }


}