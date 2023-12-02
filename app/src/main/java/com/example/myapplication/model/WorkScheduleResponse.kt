package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class WorkScheduleResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<WorkSchedule>,
    @SerializedName("msg")
    val msg: String
) {

    data class WorkSchedule(
        @SerializedName("dateTest")
        val dateTest: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("patient")
        val patient: PatientX,
        @SerializedName("statusBook")
        val statusBook: String,
        @SerializedName("timeTest")
        val timeTest: String
    ) {
        data class PatientX(
            @SerializedName("age")
            val age: Int,
            @SerializedName("avatar")
            val avatar: String,
            @SerializedName("gender")
            val gender: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("phone")
            val phone: String
        )
    }
}