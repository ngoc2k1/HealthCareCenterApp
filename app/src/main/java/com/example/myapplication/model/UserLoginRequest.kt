package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    @SerializedName("phoneOrEmail")
    val phoneOrEmail: String,
    @SerializedName("password")
    val password: String
)