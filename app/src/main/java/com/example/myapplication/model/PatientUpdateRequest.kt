package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class PatientUpdateRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("bloodGroup")
    val bloodGroup: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("healthInsurance")
    val healthInsurance: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("identityCard")
    val identityCard: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Double
)