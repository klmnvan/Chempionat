package com.example.chempionat.api

import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {
    @GET("api/Catalog")
    fun getCatalog(): Call<List<CatalogModel>>
    @GET("api/News")
    fun getNews(): Call<List<NewsModel>>
    @POST("api/SendCode")
    fun postEmail(@Header("User-email") email: String): Call<String>
    @POST("api/SignIn")
    fun postCode(@Header("User-email") email: String, @Header("User-code") code: String): Call<String>
    @POST("api/createProfile")
    fun postProfile(@Header("Authorization") bearer: String): Call<String>


/*    @POST("api/SendCode")
    fun postEmail(@Header("email") email: String): Call<String>
    @POST("api/signin")
    fun postCode(@Header("email") email: String, @Header("code") code: String): Call<TokenModel>*/
}