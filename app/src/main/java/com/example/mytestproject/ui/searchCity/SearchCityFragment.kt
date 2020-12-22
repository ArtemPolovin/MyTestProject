package com.example.mytestproject.ui.searchCity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytestproject.App
import com.example.mytestproject.R
import com.example.mytestproject.util.MINIMUM_SYMBOLS
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search_city.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchCityFragment : Fragment() {

    private var disposable: Disposable? = null

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

        setTitle()

        (activity?.applicationContext as App).weatherDataComponent.inject(this)

        searchViewModel =
            ViewModelProvider(this, searchCityFactory).get(SearchCityViewModel::class.java)

        adapter = SearchCityAdapter()

        rv_search_city.adapter = adapter
        rv_search_city.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        showLastChosenCities()

        inputResultOfCitySearch()
        sendCityListToAdapter()
        chosenCity()
        switchFragment()

    }

    private fun chosenCity() {  // the method takes city id from recyclerView by clicking on item and sends the id to ViewModel
        adapter.onClickItemListener(object :
            SearchCityAdapter.OnClickListenerCityModel {
            override fun getCityId(cityId: Int) {
                searchViewModel.onCityChosen(cityId)
                closeKeyboard()
            }
        })
    }

    private fun closeKeyboard() {
        activity?.currentFocus?.let {
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

    }

    private fun switchFragment() {
        searchViewModel.navigateToCurrentWeather.observe(
            viewLifecycleOwner, Observer {
                it.getContentIfNotHandled()?.let {
                   // requireActivity().onBackPressed()
                  // findNavController().navigate(R.id.nav_three_days_weather)
                   findNavController().popBackStack()
                }
            })
    }

    private fun sendCityListToAdapter() {
        searchViewModel.filteredCityList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    private fun resultFromSearchView(): Flowable<String> {
        return Flowable.create({ emitter ->
            search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    emitter.onNext(query ?: "")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    emitter.onNext(newText ?: "")
                    return false
                }

            })
        }, BackpressureStrategy.LATEST)
    }

    private fun inputResultOfCitySearch() { // the method sends input result to ViewModel
        disposable = resultFromSearchView()
            .filter { it.length >= MINIMUM_SYMBOLS }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { inputResult ->
                    searchViewModel.searchCity(inputResult)
                }, {
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    private fun showLastChosenCities() {
        searchViewModel.lastChosenCities.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

    private fun setTitle() {
        val activity = (activity as? AppCompatActivity)
        activity?.let {
            it.supportActionBar?.subtitle = null
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}