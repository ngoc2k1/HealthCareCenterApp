package com.example.myapplication.model

import com.example.myapplication.model.patient.BookSchedule
import com.example.myapplication.model.patient.PatientModel
import com.example.myapplication.model.patient.Specialty
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationModel(
    @SerializedName("book_schedule_id")
    val book_schedule_id: BookSchedule,
    @SerializedName("content")
    val content: String,
    @SerializedName("patient_id")
    val patient_id: PatientModel
) : Serializable
