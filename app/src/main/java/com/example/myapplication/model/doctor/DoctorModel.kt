package com.example.myapplication.model.doctor

import com.example.myapplication.model.patient.Specialty
import com.example.myapplication.utils.GENDER
import com.google.gson.annotations.SerializedName

data class DoctorModel(
    @SerializedName("specialty_id")
    val specialty_id: Specialty,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: GENDER,
    @SerializedName("address")
    val address_test: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("identity_card")
    val identity_card: String? = null,
    @SerializedName("health_insurance")
    val health_insurance: String? = null
)