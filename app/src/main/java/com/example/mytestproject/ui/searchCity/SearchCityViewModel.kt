package com.example.mytestproject.ui.searchCity

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestproject.util.CITY_ID
import com.example.mytestproject.util.CityFilter
import com.example.mytestproject.util.Event
import com.example.mytestproject.util.SHARED_PREFS
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SearchCityViewModel(
    private val context: Context,
    private val cityFilter: CityFilter
) : ViewModel() {

    private var disposable: Disposable? = null

    private val _navigateToCurrentWeather = MutableLiveData<Event<Int>>()
    val navigateToCurrentWeather: LiveData<Event<Int>> get() = _navigateToCurrentWeather

    private fun fromView(searchView: SearchView): Flowable<String> {
        return Flowable.create({ emitter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    fun searchCity(
        adapter: SearchCityAdapter,
        searchView: SearchView
    ) {
                                                //  method sends filtered list of "CityModel"  by user-entered characters to adapter
        disposable = fromView(searchView)
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { inputResult ->
                    adapter.setData(cityFilter.filterCityList(inputResult))
                },
                {
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    fun saveCityId(cityId: Int) { // The method  saves the city id to SharedPreferences
        _navigateToCurrentWeather.value = Event(cityId)

        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.putInt(CITY_ID, cityId)
        editor?.apply()
    }


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}