package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class MedicalHistoryDetailResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("bookSchedule")
        val bookSchedule: BookScheduleX,
        @SerializedName("id")
        val id: Int,
        @SerializedName("judgmentNote")
        val judgmentNote: String? = null,
        @SerializedName("prescription")
        val prescription: String? = null,
        @SerializedName("retestDate")
        val retestDate: String? = null,
        @SerializedName("testResult")
        val testResult: String? = null
    ) {
        data class BookScheduleX(
            @SerializedName("dateTest")
            val dateTest: String,
            @SerializedName("doctor")
            val doctor: DoctorX,
            @SerializedName("id")
            val id: Int,
            @SerializedName("namePatientTest")
            val namePatientTest: String? = ""
        ) {
            data class DoctorX(
                @SerializedName("addressTest")
                val addressTest: String,
                @SerializedName("avatar")
                val avatar: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("specialty")
                val specialty: SpecialtyX
            ) {
                data class SpecialtyX(
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("name")
                    val name: String
                )
            }
        }
    }
}