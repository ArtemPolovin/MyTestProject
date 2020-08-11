package com.example.mytestproject.ui.weatherData.todayWeather

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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

class WeatherDataFragment : Fragment() {

    @Inject
    lateinit var weatherDataFactory: WeatherDataFactory
    private lateinit var weatherDataViewModel: WeatherDataViewModel

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

        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null

        val animationDrawable = weather_data_fragment.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(4000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        weatherDataViewModel =
            ViewModelProvider(this, weatherDataFactory).get(WeatherDataViewModel::class.java)


        setupWeatherData()

        btnRetry.setOnClickListener {
            weatherDataViewModel.onRetry()
        }

    }

    private fun setupWeatherData() {
        weatherDataViewModel.viewState.observe(viewLifecycleOwner, Observer {
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
                is WeatherViewState.CurrentWeatherLoaded -> {
                    group_temp_abbreviation.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    group_error_views.visibility = View.GONE
                    weatherDataBinding.weatherData = it.weatherData
                }
            }
        })

    }
}
