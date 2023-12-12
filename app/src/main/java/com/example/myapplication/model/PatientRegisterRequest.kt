package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class PatientRegisterRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String
)