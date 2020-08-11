package com.example.mytestproject.ui.weatherData.dailyWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.WeatherData
import com.example.mytestproject.R
import com.example.mytestproject.databinding.CellDailyWeatherBinding

class AdapterDailyWeather(private val weatherList: List<WeatherData>) :
    RecyclerView.Adapter<AdapterDailyWeather.DailyWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val cellDailyWeather: CellDailyWeatherBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_daily_weather,
            parent,
            false
        )
        return DailyWeatherViewHolder(cellDailyWeather)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }


    class DailyWeatherViewHolder(cellDailyWeatherBinding: CellDailyWeatherBinding) :
        RecyclerView.ViewHolder(cellDailyWeatherBinding.root) {

        private val dailyWeatherBinding = cellDailyWeatherBinding

        fun bind(weatherData: WeatherData) {
            dailyWeatherBinding.weatherData = weatherData
        }
    }
}