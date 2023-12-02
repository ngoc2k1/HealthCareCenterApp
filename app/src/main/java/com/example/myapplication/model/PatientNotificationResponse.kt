package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class PatientNotificationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("bookSchedule")
        val bookSchedule: BookSchedule,
        @SerializedName("content")
        val content: String,
        @SerializedName("doctor")
        val doctor: Doctor
    ) {
        data class BookSchedule(
            @SerializedName("datTest")
            val datTest: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("timeTest")
            val timeTest: String
        )

        data class Doctor(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("specialty")
            val specialty: Specialty
        ){
            data class Specialty(
                @SerializedName("name")
                val name: String
            )
        }
    }
}