package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class Specialty(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)