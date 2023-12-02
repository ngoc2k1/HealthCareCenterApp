package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class ResetPwActiveRequest(
    @SerializedName("email")
    val email: String
)