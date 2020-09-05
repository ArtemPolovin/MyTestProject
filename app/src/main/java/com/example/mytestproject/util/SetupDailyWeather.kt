package com.example.mytestproject.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestproject.ui.weatherData.dailyWeather.TenDaysWeatherAdapter
import com.example.mytestproject.ui.weatherData.dailyWeather.ThreeDaysWeatherAdapter
import com.example.mytestproject.viewState.WeatherViewState

private lateinit var myAdapter: MyAdapter

fun showDailyWeatherRequestResult(
    viewState: WeatherViewState,
    progressBar: ProgressBar,
    errorText: TextView,
    recyclerView: RecyclerView,
    adapter: MyAdapter
) {
    when (viewState) {
        WeatherViewState.Loading -> {
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
        WeatherViewState.Error -> {
            progressBar.visibility = View.GONE
            errorText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
        is WeatherViewState.DailyWeatherLoaded -> {
            progressBar.visibility = View.GONE
            errorText.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE

            when (adapter) {
                is TenDaysWeatherAdapter -> {
                    myAdapter =  TenDaysWeatherAdapter()
                    recyclerView.adapter = myAdapter as TenDaysWeatherAdapter
                }
                else -> {
                    myAdapter = ThreeDaysWeatherAdapter()
                    recyclerView.adapter = myAdapter as ThreeDaysWeatherAdapter
                }
            }
            myAdapter.setAdapterData(viewState.dailyWeatherList)

        }
    }
}