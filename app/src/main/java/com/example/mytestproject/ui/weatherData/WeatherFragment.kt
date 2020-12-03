package com.example.mytestproject.ui.weatherData

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mytestproject.R
import com.example.mytestproject.ui.weatherData.dailyWeather.TenDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.dailyWeather.ThreeDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.todayWeather.CurrentWeatherFragment
import kotlinx.android.synthetic.main.fragment_weather.*

class WeatherFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        switchFragmentIfFragmentContainerIsEmpty(savedInstanceState)

        bottom_nav.setOnNavigationItemSelectedListener {
            switchFragment(getClickedFragment(it.itemId))
            true
        }


    }

    private fun switchFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.weather_fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun getClickedFragment(id: Int): Fragment {
        return when (id) {
            R.id.nav_today_weather -> CurrentWeatherFragment()
            R.id.nav_three_days_weather -> ThreeDaysWeatherFragment()
            else -> TenDaysWeatherFragment()
        }
    }

    private fun switchFragmentIfFragmentContainerIsEmpty(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) switchFragment(CurrentWeatherFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, navController
        ) || super.onOptionsItemSelected(item)
    }
    

}