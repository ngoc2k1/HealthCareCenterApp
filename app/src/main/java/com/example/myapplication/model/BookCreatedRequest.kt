package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class BookCreatedRequest(
    @SerializedName("doctorWorkScheduleId")
    val doctorWorkScheduleId: Int,
    @SerializedName("namePatientTest")
    val namePatientTest: String,
    @SerializedName("qrcode")
    val qrcode: String,
    @SerializedName("statusHealth")
    val statusHealth: String
)