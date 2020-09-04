package com.example.mytestproject.ui.searchCity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.CityModel
import com.example.mytestproject.R
import com.example.mytestproject.databinding.CellCityBinding

class SearchCityAdapter: RecyclerView.Adapter<SearchCityAdapter.SearchCityViewHolder>() {

    private val cityModelList = mutableListOf<CityModel>()

    private var onclickListenerCityModel: OnClickListenerCityModel? = null

    init {
        setHasStableIds(true)
    }

    fun onClickItemListener(onclickListener: OnClickListenerCityModel) {
        onclickListenerCityModel = onclickListener
    }

    fun setData(newCityList: List<CityModel>) {
        cityModelList.clear()
        cityModelList.addAll(newCityList)
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
        return cityModelList.size
    }

    override fun onBindViewHolder(holder: SearchCityViewHolder, position: Int) {
        holder.bind(cityModelList[position])
        holder.clickItem()
    }

    override fun getItemId(position: Int): Long {
        return cityModelList[position].city_id.toLong()
    }

    class SearchCityViewHolder(cityModelItem: CellCityBinding,private val  onclickListener: OnClickListenerCityModel?) :
        RecyclerView.ViewHolder(cityModelItem.root) {

        private val cellCityModel = cityModelItem

        fun bind(cityModel: CityModel) {
            cellCityModel.cityModel = cityModel
        }

        fun clickItem() {
            itemView.setOnClickListener {
               onclickListener?.getCityId(itemId.toInt())
            }
        }
    }

    interface OnClickListenerCityModel {
        fun getCityId(cityId: Int)
    }
}