package com.example.myapplication.model.doctor


import com.google.gson.annotations.SerializedName

data class DoctorLoginResponse(
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
    data class Specialty(
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("name")
        val name: String
    )
}