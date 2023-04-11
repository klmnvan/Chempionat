package com.example.chempionat.api

data class CatalogModel(
    var id: Int,
    var name: String,
    var description: String,
    var price: String,
    var category: String,
    var time_result: String,
    var preparation: String,
    var bio: String
): java.io.Serializable
