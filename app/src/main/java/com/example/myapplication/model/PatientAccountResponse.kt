package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class PatientAccountResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String
){
    data class Data(
        @SerializedName("address")
        val address: String,
        @SerializedName("age")
        val age: Int,
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("birthday")
        val birthday: String,
        @SerializedName("bloodGroup")
        val bloodGroup: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("healthInsurance")
        val healthInsurance: Any,
        @SerializedName("height")
        val height: Int,
        @SerializedName("id")
        val id: Int,
        @SerializedName("identityCard")
        val identityCard: Any,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("weight")
        val weight: Double
    )
}