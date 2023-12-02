package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class Doctor(
    @SerializedName("addressTest")
    val addressTest: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("specialty")
    val specialty: Specialty
)