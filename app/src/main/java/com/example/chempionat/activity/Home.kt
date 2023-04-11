package com.example.chempionat.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chempionat.R
import com.example.chempionat.api.*
import com.example.chempionat.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    val adapterNews = AdapterNews()
    val adapterCategory = AdapterCategory()
    val adapterCatalog = AdapterCatalog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    fun initNews(news: List<NewsModel>){
        with(binding){
            listNews.layoutManager = GridLayoutManager(this@Home, news.size)
            listNews.adapter = adapterNews
            for (element in news){
                adapterNews.addNews(element)
            }
        }
    }

    fun initCategory(category: List<String>){
        with(binding){
            listCategory.layoutManager = GridLayoutManager(this@Home, category.size)
            listCategory.adapter = adapterCategory
            for (element in category){
                adapterCategory.addCategory(element)
            }
        }
    }

    fun initCatalog(catalog: List<CatalogModel>){
        with(binding){
            listBlock.layoutManager = GridLayoutManager(this@Home, 1)
            listBlock.adapter = adapterCatalog
            for (element in catalog){
                adapterCatalog.addcatalog(element)
            }
        }
    }
    fun getData(){
        val api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://medic.madskill.ru")
            .build()
            .create(ApiRequest::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.getNews().awaitResponse()
                val responseCat = api.getCatalog().awaitResponse()
                if(response.isSuccessful){
                    runOnUiThread { initNews(response.body()!!) }
                }
                if(responseCat.isSuccessful){
                    var data = responseCat.body()!!
                    var listCategory: List<String> = data.map { it.category }.toSet().toList()
                    runOnUiThread { initCategory(listCategory) }
                    var listCatalog = data.filter { it.category == listCategory[0] }
                    runOnUiThread { initCatalog(listCatalog) }
                }
            } catch (e: Exception){
                Log.d(ContentValues.TAG, e.toString())
            }
        }
    }
}