package com.example.chempionat.models

data class PersonModel(
    var id:Int?,
    var firstname: String,
    var lastname: String,
    var middlename: String,
    var bith: String,
    var pol: String,
    var image:String
): java.io.Serializable
