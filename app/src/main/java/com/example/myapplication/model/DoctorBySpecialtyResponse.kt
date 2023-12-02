package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class DoctorBySpecialtyResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("perPage")
    val perPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
){
    data class Data(
        @SerializedName("addressTest")
        val addressTest: String,
        @SerializedName("avatar")
        val avatar: String,
        @SerializedName("birthday")
        val birthday: String,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("healthInsurance")
        val healthInsurance: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("identityCard")
        val identityCard: Any,
        @SerializedName("name")
        val name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("specialty")
        val specialty: Specialty
    )
}