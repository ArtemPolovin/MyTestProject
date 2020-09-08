package com.example.mytestproject.util

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mytestproject.ui.weatherData.dailyWeather.TenDaysWeatherAdapter
import com.example.mytestproject.ui.weatherData.dailyWeather.ThreeDaysWeatherAdapter
import com.example.mytestproject.viewState.WeatherViewState

private lateinit var myAdapter: MyAdapter

fun showDailyWeatherRequestResult(
    viewState: WeatherViewState,
    swipeRefreshLayout: SwipeRefreshLayout,
    errorText: TextView,
    recyclerView: RecyclerView,
    adapter: MyAdapter
) {

    recyclerView.visibility = View.GONE
    swipeRefreshLayout.isRefreshing = false
    errorText.visibility = View.GONE

    when (viewState) {
        WeatherViewState.Loading -> {
            swipeRefreshLayout.isRefreshing = true
        }
        WeatherViewState.Error -> {
            errorText.visibility = View.VISIBLE
        }
        is WeatherViewState.DailyWeatherLoaded -> {
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