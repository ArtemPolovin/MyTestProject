package com.example.mytestproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.mytestproject.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_second_fragment,
                R.id.nav_third_fragment,
                R.id.nav_today_weather,
                R.id.nav_three_days_weather,
                R.id.nav_ten_days_fragment,
                R.id.nav_search_city,
                R.id.weather_fragment
            ), drawer_layout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)


    /*    bottom_navigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            bottom_navigation.visibility = when (destination.id) {
                R.id.nav_second_fragment, R.id.nav_third_fragment, R.id.search_city -> View.GONE
                else -> View.VISIBLE
            }
        }*/


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
