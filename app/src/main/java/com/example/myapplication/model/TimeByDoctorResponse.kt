package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class TimeByDoctorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
){
    data class Data(
        @SerializedName("id")
        val id: Int,
        @SerializedName("isBooked")
        val isBooked: Boolean,
        @SerializedName("price")
        val price: Int,
        @SerializedName("value")
        val value: String
    )
}