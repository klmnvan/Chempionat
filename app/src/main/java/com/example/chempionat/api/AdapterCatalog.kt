package com.example.chempionat.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chempionat.R
import com.example.chempionat.databinding.ItemBlockBinding

class AdapterCatalog: RecyclerView.Adapter<AdapterCatalog.Holder>() {
    var listCatalog = ArrayList<CatalogModel>()

    class Holder(item: View): RecyclerView.ViewHolder(item){
        var binding = ItemBlockBinding.bind(item)

        fun bind(catalog: CatalogModel) = with(binding){
            TextNameBlock.setText(catalog.name)
            TextPrice.setText(catalog.price)
            TextTimeResult.setText(catalog.time_result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCatalog.Holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_block, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: AdapterCatalog.Holder, position: Int) {
        holder.bind(listCatalog[position])
    }

    override fun getItemCount(): Int {
        return listCatalog.size
    }

    fun addcatalog(catalog: CatalogModel){
        listCatalog.add(catalog)
        notifyDataSetChanged()
    }
}