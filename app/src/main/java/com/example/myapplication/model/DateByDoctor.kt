package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class DateByDoctor(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("msg")
    val msg: String
)