package com.example.myapplication.model.patient

import com.google.gson.annotations.SerializedName

data class Specialty(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
)