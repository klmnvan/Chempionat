package com.example.chempionat.api

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiRequest {
    @POST("/api/sendCode")
    fun postEmail(@Header("email") email: String): Call<String>

    @POST("/api/signin")
    fun postCode(@Header("code") code: String, @Header("email") email: String): Call<String>
}