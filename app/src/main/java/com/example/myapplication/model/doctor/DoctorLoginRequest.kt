package com.example.myapplication.model.doctor


import com.google.gson.annotations.SerializedName

data class DoctorLoginRequest(
    @SerializedName("phoneOrEmail")
    val phoneOrEmail: String,
    @SerializedName("password")
    val password: String
)