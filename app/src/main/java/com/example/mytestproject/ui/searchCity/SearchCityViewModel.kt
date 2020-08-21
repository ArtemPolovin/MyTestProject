package com.example.mytestproject.ui.searchCity

import android.content.Context
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.ViewModel
import com.example.data.utils.getCityModelsListFromJson
import com.example.domain.models.CityModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchCityViewModel : ViewModel() {

    private var disposable: Disposable? = null

    private fun fromView(searchView: SearchView): Observable<String> {

        val subject: PublishSubject<String> = PublishSubject.create()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                subject.onNext(newText ?: "")
                return true
            }
        })
        return subject
    }

    fun searchCity(adapter: SearchCityAdapter, context: Context, searchView: SearchView) {
        disposable = fromView(searchView)
            .subscribeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { inputResult ->
                    val list = arrayListOf<CityModel>()
                    list.addAll(getCityModelsListFromJson(context).toList().filter {
                        it.city_name.contains(inputResult, true)
                    })
                    adapter.setData(list)

                }, {
                    Log.i("ERROR", "error = ${it.printStackTrace()}")
                }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}