package com.example.mytestproject.ui.weatherData

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
import com.example.mytestproject.databinding.WeatherDataFragmentBinding
import com.example.mytestproject.ui.viewState.WeatherViewState
import kotlinx.android.synthetic.main.weather_data_fragment.*
import javax.inject.Inject

class WeatherDataFragment : Fragment() {

    @Inject
    lateinit var weatherDataViewModelFactory: WeatherViewModelFactory

    private lateinit var weatherDataViewModel: WeatherDataViewModel

    private lateinit var weatherDataBinding: WeatherDataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        weatherDataBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.weather_data_fragment, container, false
        )

        return weatherDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationDrawable = fragment_1_background.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(4000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        (activity?.applicationContext as App).appComponent.injectWeatherDataFragment(this)

        weatherDataViewModel =
            ViewModelProvider(this, weatherDataViewModelFactory).get(
                WeatherDataViewModel::class.java
            )

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
