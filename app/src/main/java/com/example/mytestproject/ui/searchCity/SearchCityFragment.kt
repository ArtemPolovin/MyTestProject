package com.example.mytestproject.ui.searchCity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.CityModel
import com.example.mytestproject.R
import com.example.mytestproject.util.CITY_MODEL
import com.example.mytestproject.util.SHARED_PREFS
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_search_city.*

class SearchCityFragment : Fragment() {

    private lateinit var adapter: SearchCityAdapter
    private val searchViewModel: SearchCityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchCityAdapter()

        rv_search_city.adapter = adapter
        rv_search_city.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchViewModel.searchCity(adapter, requireContext(), search_view)

        saveCityId()

    }

    private fun saveCityId() {
        adapter.attachCityModel(object :
            SearchCityAdapter.OnClickListenerCityModel { // the method takes object from recyclerView by clicking on item and saves the object to SharedPreferences
            override fun getCityModel(cityModel: CityModel) {
                val json = Gson().toJson(cityModel)
                val sharedPreferences =
                    context?.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                editor?.putString(CITY_MODEL, json)
                editor?.apply()
                switchFragment()
            }
        })
    }

    private fun switchFragment() {
        findNavController().navigate(R.id.action_searchCityFragment_to_nav_today_weather)
    }

}