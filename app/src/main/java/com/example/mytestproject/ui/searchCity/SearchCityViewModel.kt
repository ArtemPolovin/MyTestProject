package com.example.mytestproject.ui.searchCity

import android.content.Context
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.ViewModel
import com.example.data.utils.getCityModelsListFromJson
import com.example.domain.models.CityModel
import com.example.mytestproject.App
import com.example.mytestproject.util.CityFilter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchCityViewModel(context:Context) : ViewModel() {

    @Inject
    lateinit var cityFilter:CityFilter

    init {
        (context.applicationContext as App).weatherDataComponent.inject(this)
    }

    private var disposable: Disposable? = null

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

    fun searchCity(adapter: SearchCityAdapter, searchView: SearchView) { //  method sends filtered list of "CityModel"  by user-entered characters to adapter
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

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}