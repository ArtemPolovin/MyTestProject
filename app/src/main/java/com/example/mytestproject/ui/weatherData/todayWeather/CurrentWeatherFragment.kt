package com.example.mytestproject.ui.weatherData.todayWeather

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.mytestproject.App
import com.example.mytestproject.R
import com.example.mytestproject.databinding.CurrentWeatherDataFragmentBinding
import com.example.mytestproject.viewState.WeatherViewState
import kotlinx.android.synthetic.main.current_weather_data_fragment.*
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    @Inject
    lateinit var currentWeatherFactory: CurrentWeatherFactory
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel

    private lateinit var weatherDataBinding: CurrentWeatherDataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        weatherDataBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.current_weather_data_fragment, container, false
        )

        return weatherDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setTitle()

        (weather_data_fragment.background as AnimationDrawable).run {
            setEnterFadeDuration(4000)
            setExitFadeDuration(4000)
            start()
        }     //screen background animation

        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        currentWeatherViewModel =
            ViewModelProvider(this, currentWeatherFactory).get(CurrentWeatherViewModel::class.java)


        setupWeatherData()

        btn_retry.setOnClickListener {
            currentWeatherViewModel.onRetry()
        }

        refreshingWeather()
    }

    private fun setupWeatherData() {
        currentWeatherViewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                WeatherViewState.Loading -> {
                    group_temp_abbreviation.visibility = View.GONE
                    refresh_layout.isRefreshing = true
                    group_error_views.visibility = View.GONE
                }
                WeatherViewState.Error -> {
                    group_temp_abbreviation.visibility = View.GONE
                    refresh_layout.isRefreshing = false
                    group_error_views.visibility = View.VISIBLE
                }
                is WeatherViewState.CurrentWeatherLoaded -> {
                    group_temp_abbreviation.visibility = View.VISIBLE
                    refresh_layout.isRefreshing = false
                    group_error_views.visibility = View.GONE
                    weatherDataBinding.weatherData = it.weatherData
                }
            }
        })

    }

    private fun setTitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }

    private fun refreshingWeather() {
        refresh_layout.setOnRefreshListener {
            currentWeatherViewModel.refreshWeatherDataList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}
