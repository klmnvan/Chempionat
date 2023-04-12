package com.example.chempionat.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chempionat.R
import com.example.chempionat.activity.Home
import com.example.chempionat.databinding.ItemButtonBinding

class AdapterCategory(private val listener: Home): RecyclerView.Adapter<AdapterCategory.Holder>() {
    var listCategory = ArrayList<String>()

    class Holder(item: View): RecyclerView.ViewHolder(item){
        var binding = ItemButtonBinding.bind(item)

        fun bind(category: String, listener: Listener) = with(binding){
            ButtonCatalog.text = category
            ButtonCatalog.setOnClickListener(){
                listener.getCatalog(category, adapterPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategory.Holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(listCategory[position], listener)
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    fun addCategory(category: String){
        listCategory.add(category)
        notifyDataSetChanged()
    }

    interface Listener{
        fun getCatalog(catalog: String, position: Int)
    }
}