package com.example.myapplication.model.doctor

import com.google.gson.annotations.SerializedName

data class DoctorLoginRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("token")
    val token: String
)