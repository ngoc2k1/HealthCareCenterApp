package com.example.myapplication.model.patient

import com.example.myapplication.utils.GENDER
import com.google.gson.annotations.SerializedName

data class PatientLoginRequest(

    @SerializedName("phone")
    val phone: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("token")
    val token: String
)