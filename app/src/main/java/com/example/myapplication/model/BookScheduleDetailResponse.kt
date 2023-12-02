package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class BookScheduleDetailResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String
) {

    data class Data(
        @SerializedName("dateTest")
        val dateTest: String,
        @SerializedName("doctor")
        val doctor: Doctor,
        @SerializedName("id")
        val id: Int,
        @SerializedName("namePatientTest")
        val namePatientTest: String? = "",
        @SerializedName("patient")
        val patient: Patient,
        @SerializedName("price")
        val price: Int,
        @SerializedName("qrCode")
        val qrCode: String,
        @SerializedName("statusBook")
        val statusBook: String,
        @SerializedName("statusHealth")
        val statusHealth: String,
        @SerializedName("timeTest")
        val timeTest: String
    )
}