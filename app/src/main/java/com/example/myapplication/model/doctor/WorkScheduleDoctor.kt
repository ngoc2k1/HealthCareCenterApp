package com.example.myapplication.model.doctor

import com.google.gson.annotations.SerializedName

data class WorkScheduleDoctor(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("doctor_id")
    val doctor_id: DoctorModel,
    @SerializedName("duration_test")
    val duration_test: Int,
    @SerializedName("work_time")
    val work_time: String,
    @SerializedName("work_start_at")
    val work_start_at: String,
    @SerializedName("work_end_at")
    val work_end_at: String
)