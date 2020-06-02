package com.example.mytestproject.tomorrowWeather

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.mytestproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_first.*

class TomorrowWeatherFragment : Fragment() {

    private val tomorrowWeatherViewModel: TomorrowWeatherViewModel by viewModels()
    private val key = "40a7956799be42f49bc8b6ac4bb8e432"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationDrawable = fragment_1_background.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(4000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        setupTomorrowWeatherData()

    }

    private fun setupTomorrowWeatherData() {
        tomorrowWeatherViewModel.getTomorrowWeatherData(key, "Moscow", 1, "M")
        tomorrowWeatherViewModel.tomorrowWeatherData.observe(viewLifecycleOwner, Observer {
            textTemperature.text = it.data[0].temp.toString()
            textLocation.text = it.city_name
            getIcon(it.data[0].weather.icon)
        })
    }

    private fun getIcon(iconId: String) {
        Picasso.get()
            .load("https://www.weatherbit.io/static/img/icons/$iconId.png")
            .placeholder(R.drawable.ic_cloud_download)
            .resize(300,300)
            .error(R.drawable.ic_error)
            .into(imageWeatherIcon)

    }

}
