package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class ChangePwResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    var `data`: String? = "",
    @SerializedName("msg")
    val msg: String
)