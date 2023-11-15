package com.example.myapplication.model.patient

import com.example.myapplication.utils.GENDER
import com.google.gson.annotations.SerializedName

data class PatientModel(
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
    val address: String,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("identity_card")
    val identity_card: String? = null,
    @SerializedName("health_insurance")
    val health_insurance: String? = null
)