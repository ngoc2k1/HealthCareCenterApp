package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class MedicalHistoryUpdateRequest(
    @SerializedName("judgmentNote")
    var judgmentNote: String? = "",
    @SerializedName("prescription")
    var prescription: String? = "",
    @SerializedName("retestDate")
    var retestDate: String? = "",
    @SerializedName("testResult")
    var testResult: String? = ""
)