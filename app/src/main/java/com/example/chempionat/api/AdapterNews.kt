package com.example.chempionat.api

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chempionat.R
import com.example.chempionat.databinding.ItemNewBinding

class AdapterNews: RecyclerView.Adapter<AdapterNews.Holder>() {
    var listNews = ArrayList<NewsModel>()

    class Holder(item: View): RecyclerView.ViewHolder(item){
        var binding = ItemNewBinding.bind(item)
        fun bind (news: NewsModel){
            binding.textCheckUp.text = news.name
            binding.textIssled.text = news.description
            binding.textSumma.text = news.price + " ₽"
            Glide.with(binding.root).load(news.image).into(binding.pictureOneBlock)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNews.Holder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_new, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: AdapterNews.Holder, position: Int) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    fun addNews(news: NewsModel){
        listNews.add(news)
        notifyDataSetChanged()
    }
}