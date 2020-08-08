package com.example.mytestproject.ui.weatherData.todayWeather

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mytestproject.App
import com.example.mytestproject.R
import com.example.mytestproject.databinding.TodayWeatherDataFragmentBinding
import com.example.mytestproject.viewState.WeatherViewState
import kotlinx.android.synthetic.main.today_weather_data_fragment.*
import javax.inject.Inject

class TodayWeatherDataFragment : Fragment() {

    @Inject
    lateinit var todayWeatherDataFactory: TodayWeatherDataFactory
    private lateinit var todayWeatherDataViewModel: TodayWeatherDataViewModel

    private lateinit var weatherDataBinding: TodayWeatherDataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        weatherDataBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.today_weather_data_fragment, container, false
        )

        return weatherDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationDrawable = weather_data_fragment.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(4000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        (activity?.applicationContext as App).weatherDataComponent.injectWeatherDataFragment(this)

        todayWeatherDataViewModel =
            ViewModelProvider(this, todayWeatherDataFactory).get(TodayWeatherDataViewModel::class.java)

        setupWeatherData()

        btnRetry.setOnClickListener {
            todayWeatherDataViewModel.onRetry()
        }

    }

    private fun setupWeatherData() {
        todayWeatherDataViewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                WeatherViewState.Loading -> {
                    group_temp_abbreviation.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    group_error_views.visibility = View.GONE
                }
                WeatherViewState.Error -> {
                    group_temp_abbreviation.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    group_error_views.visibility = View.VISIBLE
                }
                is WeatherViewState.WeatherLoaded -> {
                    group_temp_abbreviation.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    group_error_views.visibility = View.GONE
                    weatherDataBinding.weatherData = it.weatherData
                }
            }
        })

    }
}
