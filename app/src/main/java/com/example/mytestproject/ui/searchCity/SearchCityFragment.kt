package com.example.mytestproject.ui.searchCity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestproject.App
import com.example.mytestproject.R
import com.example.mytestproject.util.CITY_ID
import com.example.mytestproject.util.SHARED_PREFS
import kotlinx.android.synthetic.main.fragment_search_city.*
import javax.inject.Inject

class SearchCityFragment : Fragment() {

    private lateinit var adapter: SearchCityAdapter

    @Inject
    lateinit var searchCityFactory: SearchCityFactory
    private lateinit var searchViewModel: SearchCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        searchViewModel =
            ViewModelProvider(this, searchCityFactory).get(SearchCityViewModel::class.java)

        adapter = SearchCityAdapter()

        rv_search_city.adapter = adapter
        rv_search_city.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        searchViewModel.searchCity(adapter, search_view)

        saveCityId()

    }

    private fun saveCityId() {
        adapter.onClickItemListener(object :
            SearchCityAdapter.OnClickListenerCityModel { // the method takes object from recyclerView by clicking on item and saves the object to SharedPreferences
            override fun getCityModel(cityId: Int) {
                val sharedPreferences =
                    context?.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                editor?.putInt(CITY_ID, cityId)
                editor?.apply()
                switchFragment()
            }
        })
    }

    private fun switchFragment() {
        findNavController().navigate(R.id.action_searchCityFragment_to_nav_today_weather)
    }

}