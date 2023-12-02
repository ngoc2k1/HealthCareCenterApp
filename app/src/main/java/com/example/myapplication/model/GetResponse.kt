package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class GetResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)