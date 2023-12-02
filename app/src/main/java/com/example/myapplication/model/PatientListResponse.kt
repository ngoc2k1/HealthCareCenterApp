package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class PatientListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<PatientProfile>,
    @SerializedName("msg")
    val msg: String
)