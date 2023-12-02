package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class Patient(
    @SerializedName("age")
    val age: Int,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)