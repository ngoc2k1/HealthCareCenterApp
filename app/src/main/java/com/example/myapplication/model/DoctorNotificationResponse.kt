package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class DoctorNotificationResponse(
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
        val bookSchedule: BookSchedule,
        @SerializedName("content")
        val content: String,
        @SerializedName("patient")
        val patient: Patient
    ) {
        data class BookSchedule(
            @SerializedName("datTest")
            val datTest: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("statusBook")
            val statusBook: String,
            @SerializedName("timeTest")
            val timeTest: String
        )

        data class Patient(
            @SerializedName("age")
            val age: Int,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String
        )
    }
}