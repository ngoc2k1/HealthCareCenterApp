package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class ChangePwRequest(
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("password")
    val password: String
)