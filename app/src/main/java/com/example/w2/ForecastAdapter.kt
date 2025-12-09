package com.example.w2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ForecastItem(
    val date: String,
    val weather: String,
    val tempRange: String,
    val wind: String
)

class ForecastAdapter(private val items: List<ForecastItem>) : RecyclerView.Adapter<ForecastAdapter.VH>() {
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvWeather: TextView = itemView.findViewById(R.id.tvWeather)
        val tvTempRange: TextView = itemView.findViewById(R.id.tvTempRange)
        val tvWind: TextView = itemView.findViewById(R.id.tvWind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.tvDate.text = item.date
        holder.tvWeather.text = item.weather
        holder.tvTempRange.text = item.tempRange
        holder.tvWind.text = item.wind
    }

    override fun getItemCount(): Int = items.size
}

