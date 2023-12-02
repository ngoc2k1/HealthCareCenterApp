package com.example.myapplication.model.doctor


import com.example.myapplication.model.Specialty
import com.google.gson.annotations.SerializedName

data class DoctorAccountResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("msg")
    val msg: String
){
    data class Data(
        @SerializedName("addressTest")
        val addressTest: String,
        @SerializedName("age")
        val age: Int,
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("birthday")
        val birthday: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("healthInsurance")
        val healthInsurance: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("identityCard")
        val identityCard: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("specialty")
        val specialty: Specialty
    )
}