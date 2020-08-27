package com.example.mytestproject.ui.searchCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestproject.App
import com.example.mytestproject.R
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

        searchViewModel.searchCity(search_view)

        sendCityListToAdapter()
        chosenCity()
        switchFragment()

    }

    private fun chosenCity() {  // the method takes city id from recyclerView by clicking on item and saves the city id to SharedPreferences
        adapter.onClickItemListener(object :
            SearchCityAdapter.OnClickListenerCityModel {
            override fun getCityId(cityId: Int) {
                searchViewModel.saveCityId(cityId)
            }
        })
    }

    private fun switchFragment() {
        searchViewModel.navigateToCurrentWeather.observe(
            viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                    findNavController().navigate(R.id.action_searchCityFragment_to_nav_today_weather)
                }
            })
    }

    private fun sendCityListToAdapter() {
        searchViewModel.filteredCityList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

}