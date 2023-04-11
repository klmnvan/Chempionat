package com.example.chempionat.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chempionat.R
import com.example.chempionat.databinding.ItemButtonBinding

class AdapterCategory: RecyclerView.Adapter<AdapterCategory.Holder>() {
    var listCategory = ArrayList<String>()

    class Holder(item: View): RecyclerView.ViewHolder(item){
        var binding = ItemButtonBinding.bind(item)

        fun bind(category: String) = with(binding){
            ButtonCatalog.text = category
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategory.Holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listCategory[position])
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    fun addCategory(category: String){
        listCategory.add(category)
        notifyDataSetChanged()
    }
}