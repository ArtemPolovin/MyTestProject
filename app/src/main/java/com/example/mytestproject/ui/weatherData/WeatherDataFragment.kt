package com.example.mytestproject.ui.weatherData

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mytestproject.R
import com.example.mytestproject.databinding.WeatherDataFragmentBinding
import kotlinx.android.synthetic.main.weather_data_fragment.*

class WeatherDataFragment : Fragment() {

    private val weatherDataViewModel: WeatherDataViewModel by viewModels()

    private lateinit var weatherDataBinding: WeatherDataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        weatherDataBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.weather_data_fragment, container, false)

        return weatherDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationDrawable = fragment_1_background.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(4000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        setupWeatherData()
    }

    private fun setupWeatherData() {
        weatherDataViewModel.weatherDataApi.observe(viewLifecycleOwner, Observer {
            weatherDataBinding.weatherData = it
        })
    }

}