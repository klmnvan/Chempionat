package com.example.chempionat.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiRequest {
    @GET("/api/news")
    fun getCatalog(): Call<List<CatalogModel>>
    @GET("/api/news")
    fun getNews(): Call<List<NewsModel>>
    @POST("/api/sendCode")
    fun postEmail(@Header("email") email: String): Call<String>

    @POST("/api/signin")
    fun postCode(@Header("code") code: String, @Header("email") email: String): Call<String>
}