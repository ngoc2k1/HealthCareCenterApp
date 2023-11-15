package com.example.myapplication.model.patient

import com.example.myapplication.model.doctor.DoctorModel
import com.example.myapplication.utils.STATUS_BOOK
import com.google.gson.annotations.SerializedName

data class BookSchedule(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("patient_id")
    val patient_id: PatientModel,
    @SerializedName("doctor_id")
    val doctor_id: DoctorModel,
    @SerializedName("status_book")
    val status_book: STATUS_BOOK,
    @SerializedName("date_test")
    val date_test: String,
    @SerializedName("time_test")
    val time_test: String,
    @SerializedName("name_patient_test")
    val name_patient_test: String,
    @SerializedName("status_health")
    val status_health: String? = null,
    @SerializedName("qrcode")
    val qrcode: String,
    @SerializedName("price")
    val price: Int
)