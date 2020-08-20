package com.example.mytestproject.ui.weatherData.dailyWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.WeatherData
import com.example.mytestproject.R
import com.example.mytestproject.databinding.CellThreeDaysWeatherBinding
import com.example.mytestproject.util.MyAdapter

class ThreeDaysWeatherAdapter :
    RecyclerView.Adapter<ThreeDaysWeatherAdapter.ThreeDaysWeatherViewHolder>(), MyAdapter {

    private val weatherDataList = mutableListOf<WeatherData>()

    override fun setAdapterData(newWeatherDataList: List<WeatherData>) {
        weatherDataList.clear()
        weatherDataList.addAll(newWeatherDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreeDaysWeatherViewHolder {
        val weatherDataBinding: CellThreeDaysWeatherBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_three_days_weather,
            parent,
            false
        )
        return ThreeDaysWeatherViewHolder(weatherDataBinding)
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    override fun onBindViewHolder(holder: ThreeDaysWeatherViewHolder, position: Int) {
        holder.bind(weatherDataList[position])
    }

    class ThreeDaysWeatherViewHolder(weatherDataBinding :CellThreeDaysWeatherBinding) :
        RecyclerView.ViewHolder(weatherDataBinding.root) {

        private val weatherData = weatherDataBinding

        fun bind(weatherData: WeatherData) {
            this.weatherData.weatherData = weatherData
        }

    }
}