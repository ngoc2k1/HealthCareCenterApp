package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class BookScheduleRequest(
    @SerializedName("doctorWorkScheduleId")
    var doctorWorkScheduleId: Int,
    @SerializedName("namePatientTest")
    var namePatientTest: String? = " ",
    @SerializedName("qrcode")
    val qrcode: String,
    @SerializedName("statusHealth")
    var statusHealth: String? = " ",
)