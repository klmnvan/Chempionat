package com.example.chempionat.activity

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.chempionat.api.*
import com.example.chempionat.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity(), AdapterCategory.Listener {
    lateinit var binding: ActivityHomeBinding
    val adapterNews = AdapterNews()
    val adapterCategory = AdapterCategory(this)
    val adapterCatalog = AdapterCatalog()
    lateinit var refreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.refreshLayout.setOnRefreshListener {
            getData()
            binding.refreshLayout.isRefreshing = false
        }
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

    lateinit var allListCatalog: List<CatalogModel>
    fun getData(){
        val api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://iis.ngknn.ru/NGKNN/%D0%9C%D0%B0%D0%BC%D1%88%D0%B5%D0%B2%D0%B0%D0%AE%D0%A1/MedicMadlab/")
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
                    val data = responseCat.body()!!
                    val listCategory = data.map { it.category }.toSet().toList()
                    runOnUiThread { initCategory(listCategory) }
                    allListCatalog = data
                    val listCatalog = allListCatalog.filter { it.category == listCategory[0] }
                    runOnUiThread { initCatalog(listCatalog) }
                }
            } catch (e: Exception){
                Log.d(ContentValues.TAG, e.toString())
            }
        }
    }

    override fun getCatalog(catalog: String, position: Int) {
        adapterCatalog.listCatalog.clear()
        val listCatalog = allListCatalog.filter { it.category == catalog }
        initCatalog(listCatalog)
    }

    fun onRefresh() {
        getData()
        Log.d(TAG, "Обновление началось")
    }
}