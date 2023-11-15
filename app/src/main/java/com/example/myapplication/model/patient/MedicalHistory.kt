package com.example.myapplication.model.patient

import com.google.gson.annotations.SerializedName

data class MedicalHistory(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("book_schedule_id")
    val book_schedule_id: BookSchedule,
    @SerializedName("retest_date")
    val retest_date: String? = null,
    @SerializedName("judgment_note")
    val judgment_note: String? = null,
    @SerializedName("test_result")
    val test_result: String? = null,
    @SerializedName("prescription")
    val prescription: String? = null
)