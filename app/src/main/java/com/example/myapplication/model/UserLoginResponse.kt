package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class UserLoginResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String
){
    data class Data(
        @SerializedName("token")
        val token: String
    )
}