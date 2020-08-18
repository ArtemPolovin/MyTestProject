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
import com.example.mytestproject.util.showDailyWeatherRequestResult
import kotlinx.android.synthetic.main.fragment_three_days_weather.*
import javax.inject.Inject


class ThreeDaysWeatherFragment : Fragment() {

    @Inject
    lateinit var dailyWeatherFactory: DailyWeatherFactory
    private lateinit var dailyWeatherViewModel: DailyWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_three_days_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        getTitle("Moscow")

        dailyWeatherViewModel =
            ViewModelProvider(this, dailyWeatherFactory).get(DailyWeatherViewModel::class.java)

        dailyWeatherViewModel.getWeatherData(4) // one more day because the first current day is not included in the list

        context.let {
            rv_three_days_weather.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
        }

        setupDailyWeatherData()

    }

    private fun setupDailyWeatherData() {

        dailyWeatherViewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            showDailyWeatherRequestResult(
                viewState,
                progressbar,
                txt_error,
                rv_three_days_weather,
                ThreeDaysWeatherAdapter()
            )
        })

    }

    private fun getTitle(cityName: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            cityName.capitalize()
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "3 days"
    }

}