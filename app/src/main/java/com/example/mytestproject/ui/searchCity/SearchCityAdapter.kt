package com.example.mytestproject.ui.searchCity

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.CityModel
import com.example.mytestproject.R
import com.example.mytestproject.databinding.CellCityBinding

class SearchCityAdapter: RecyclerView.Adapter<SearchCityAdapter.SearchCityViewHolder>() {

    private val cityModelList = mutableListOf<CityModel>()
    private val displayList = mutableListOf<CityModel>()

    private var onclickListenerCityModel: OnClickListenerCityModel? = null

    fun attachCityModel(onclickListener: OnClickListenerCityModel) {
        onclickListenerCityModel = onclickListener
    }

    fun setData(newCityList: List<CityModel>) {
        cityModelList.clear()
        cityModelList.addAll(newCityList)
    }

    fun filter(query: String) {
        displayList.clear()
        displayList.addAll(cityModelList.filter {
            it.city_name.contains(query, true)
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCityViewHolder {
        val cellCityModel: CellCityBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.cell_city,
            parent,
            false
        )
        return SearchCityViewHolder(cellCityModel, onclickListenerCityModel)
    }

    override fun getItemCount(): Int {
        return displayList.size
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        holder.bind(displayList[position])
        holder.clickItem(displayList[position])
    }

    class SearchCityViewHolder(cityModelItem: CellCityBinding,private val  onclickListener: OnClickListenerCityModel?) :
        RecyclerView.ViewHolder(cityModelItem.root) {

        private val cellCityModel = cityModelItem

        fun bind(cityModel: CityModel) {
            cellCityModel.cityModel = cityModel
        }

        fun clickItem(cityModel: CityModel) {
            itemView.setOnClickListener {
               onclickListener?.getCityModel(cityModel)
            }
        }
    }

    interface OnClickListenerCityModel {
        fun getCityModel(cityModel: CityModel)
    }
}