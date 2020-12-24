package com.example.mytestproject.ui.weatherData.weathercontainer

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytestproject.R
import com.example.mytestproject.ui.weatherData.dailyWeather.TenDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.dailyWeather.ThreeDaysWeatherFragment
import com.example.mytestproject.ui.weatherData.todayWeather.CurrentWeatherFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
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

       // navController = Navigation.findNavController(view)

        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.local_nav_host_fragment) as NavHostFragment
        navController = nestedNavHostFragment.navController

        bottom_nav.setupWithNavController(navController)

        navController.navigate(R.id.weahter_fragment_graph)


       // navController.popBackStack(R.id.nav_today_weather,true)




     /*   bottom_nav.setOnNavigationItemSelectedListener {
            weatherContainerViewModel.receivePreviousFragmentId(it.itemId)
            switchFragment(getClickedFragment(it.itemId))
            true
        }*/

            //switchFragmentIfFragmentContainerIsEmpty(savedInstanceState)
        //returnToPreviousWeatherScreen()

    }

 /*   private fun switchFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.weather_fragment_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }*/

 /*   private fun getClickedFragment(id: Int): Fragment {
        return when (id) {
            R.id.nav_today_weather -> CurrentWeatherFragment()
            R.id.nav_three_days_weather -> ThreeDaysWeatherFragment()
            else -> TenDaysWeatherFragment()
        }
    }*/

 /*   private fun switchFragmentIfFragmentContainerIsEmpty(savedInstanceState: Bundle?) {
        if (savedInstanceState == null ) {
            switchFragment(CurrentWeatherFragment())
        }
    }*/

   /* private fun returnToPreviousWeatherScreen() {
        weatherContainerViewModel.previousFragmentId.observe(viewLifecycleOwner, Observer {
            if (it != null) switchFragment(getClickedFragment(it))
        })
    }*/

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