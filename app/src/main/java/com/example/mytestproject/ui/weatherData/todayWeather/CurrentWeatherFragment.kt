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
import com.example.mytestproject.databinding.TodayWeatherDataFragmentBinding
import com.example.mytestproject.viewState.WeatherViewState
import kotlinx.android.synthetic.main.today_weather_data_fragment.*
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    @Inject
    lateinit var currentWeatherFactory: CurrentWeatherFactory
    private lateinit var currentWeatherViewModel: CurrentWeatherViewModel

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
        setHasOptionsMenu(true)

        setTitle()

        val animationDrawable = weather_data_fragment.background as AnimationDrawable //screen background animation
        animationDrawable.setEnterFadeDuration(4000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        currentWeatherViewModel =
            ViewModelProvider(this, currentWeatherFactory).get(CurrentWeatherViewModel::class.java)


        setupWeatherData()

        btnRetry.setOnClickListener {
            currentWeatherViewModel.onRetry()
        }
    }

    private fun setupWeatherData() {  //the method shows information on UI, depending on what data comes from api
        currentWeatherViewModel.viewState.observe(viewLifecycleOwner, Observer {
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

    private fun setTitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
     inflater.inflate(R.menu.menu_toolbar, menu)
     super.onCreateOptionsMenu(menu,inflater)
 }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,requireView().findNavController()
        )|| super.onOptionsItemSelected(item)
    }
}
