package com.example.myapplication.model.doctor


import com.google.gson.annotations.SerializedName

data class DoctorSpecialtyResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
)