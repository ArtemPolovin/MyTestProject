package com.example.mytestproject.ui.weatherData.dailyWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestproject.App
import com.example.mytestproject.R
import com.example.mytestproject.util.ELEVEN_DAYS
import com.example.mytestproject.util.loadCityModel
import com.example.mytestproject.util.showDailyWeatherRequestResult
import kotlinx.android.synthetic.main.fragment_ten_days_weather.*
import javax.inject.Inject

class TenDaysWeatherFragment : Fragment() {

    @Inject
    lateinit var dailyWeatherFactory: DailyWeatherFactory
    private lateinit var dailyWeatherViewModel: DailyWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ten_days_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        getTitle(loadCityModel(view.context).city_name)

        dailyWeatherViewModel =
            ViewModelProvider(this, dailyWeatherFactory).get(DailyWeatherViewModel::class.java)

        dailyWeatherViewModel.getWeatherData(ELEVEN_DAYS) // one more day because the first current day is not included in the list

        context.let {
            rv_ten_days_weather.layoutManager = LinearLayoutManager(
                it,
                LinearLayoutManager.VERTICAL, false
            )
        }

        setupDailyWeatherData()
    }

    private fun setupDailyWeatherData() {  //the method shows information on UI, depending on what data comes from api
        dailyWeatherViewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            showDailyWeatherRequestResult(
                viewState,
                progressbar_ten_days_Weather,
                txt_ten_days_weather_error,
                rv_ten_days_weather
            )
        })

    }

    private fun getTitle(cityName: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            cityName.capitalize()
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "10 days"
    }

}