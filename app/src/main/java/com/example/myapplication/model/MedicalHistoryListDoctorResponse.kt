package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class MedicalHistoryListDoctorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
) {
    data class Data(
        @SerializedName("bookSchedule")
        val bookSchedule: BookScheduleX,
        @SerializedName("id")
        val id: Int,
        @SerializedName("judgmentNote")
        val judgmentNote: String? = "",
        @SerializedName("prescription")
        val prescription: String? = "",
        @SerializedName("retestDate")
        val retestDate: String? = "",
        @SerializedName("testResult")
        val testResult: String? = ""
    ) {
        data class BookScheduleX(
            @SerializedName("dateTest")
            val dateTest: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("namePatientTest")
            val namePatientTest: String? = ""
        )
    }
}