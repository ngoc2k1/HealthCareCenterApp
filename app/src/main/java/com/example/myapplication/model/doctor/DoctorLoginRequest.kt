package com.example.myapplication.model.doctor


import com.google.gson.annotations.SerializedName

data class DoctorLoginRequest(
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneOrEmail")
    val phoneOrEmail: String
)