package com.example.mytestproject.ui.weatherData.dailyWeather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.WeatherData
import com.example.mytestproject.R
import com.example.mytestproject.databinding.CellTenDaysWeatherBinding
import com.example.mytestproject.util.MyAdapter

class TenDaysWeatherAdapter :
    RecyclerView.Adapter<TenDaysWeatherAdapter.TenDaysWeatherViewHolder>(), MyAdapter {

    private val weatherList = mutableListOf<WeatherData>()

    override fun setAdapterData(newWeatherDataList: List<WeatherData>) {
        weatherList.clear()
        weatherList.addAll(newWeatherDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenDaysWeatherViewHolder {
        val cellWeatherDataBinding: CellTenDaysWeatherBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_ten_days_weather,
            parent,
            false
        )
        return TenDaysWeatherViewHolder(cellWeatherDataBinding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: TenDaysWeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }


    class TenDaysWeatherViewHolder(cellWeatherDataBinding: CellTenDaysWeatherBinding) :
        RecyclerView.ViewHolder(cellWeatherDataBinding.root) {

        private val weatherDataBinding = cellWeatherDataBinding

        fun bind(weatherData: WeatherData) {
            weatherDataBinding.weatherData = weatherData
        }
    }
}