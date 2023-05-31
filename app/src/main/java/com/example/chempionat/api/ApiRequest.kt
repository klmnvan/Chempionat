package com.example.chempionat.api

import com.example.chempionat.models.TokenModel
import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {
    @GET("/api/catalog")
    fun getCatalog(): Call<List<CatalogModel>>
    @GET("/api/news")
    fun getNews(): Call<List<NewsModel>>
    @POST("api/SendCode")
    fun postEmail(@Header("User-email") email: String): Call<String>
    @POST("api/SignIn")
    fun postCode(@Header("User-email") email: String, @Header("User-code") code: String): Call<TokenModel>
}