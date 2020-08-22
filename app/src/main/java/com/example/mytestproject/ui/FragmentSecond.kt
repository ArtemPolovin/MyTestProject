package com.example.mytestproject.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.models.CityModel
import com.example.mytestproject.R
import com.example.mytestproject.ui.searchCity.SearchCityAdapter
import kotlinx.android.synthetic.main.fragment_search_city.*


class FragmentSecond : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

}
