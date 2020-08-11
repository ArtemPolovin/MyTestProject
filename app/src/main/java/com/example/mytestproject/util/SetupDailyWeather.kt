package com.example.mytestproject.util

import android.view.View
import android.widget.Adapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestproject.ui.weatherData.dailyWeather.AdapterDailyWeather
import com.example.mytestproject.viewState.WeatherViewState

fun showDailyWeatherRequestResult(
    viewState: WeatherViewState,
    progressBar: ProgressBar,
    text: TextView,
    recyclerView: RecyclerView
) {
    when (viewState) {
        WeatherViewState.Loading -> {
            progressBar.visibility = View.VISIBLE
        }
        WeatherViewState.Error -> {
            progressBar.visibility = View.GONE
            text.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
        is WeatherViewState.DailyWeatherLoaded -> {
            progressBar.visibility = View.GONE
            text.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = AdapterDailyWeather(viewState.dailyWeatherList)
        }
    }
}