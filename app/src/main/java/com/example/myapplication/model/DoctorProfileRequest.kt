package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class DoctorProfileRequest(
    @SerializedName("addressTest")
    val addressTest: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("healthInsurance ")
    val healthInsurance: String,
    @SerializedName("identityCard ")
    val identityCard: String,
    @SerializedName("name")
    val name: String
)