package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class ResetPwRequest(
    @SerializedName("newPassword")
    val newPassword: String,
    @SerializedName("resetPasswordCode")
    val resetPasswordCode: String
)