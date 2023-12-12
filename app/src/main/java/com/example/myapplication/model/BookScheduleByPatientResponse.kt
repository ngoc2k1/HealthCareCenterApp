package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class BookScheduleByPatientResponse(
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
        @SerializedName("dateTest")
        val dateTest: String,
        @SerializedName("doctor")
        val doctor: DoctorX,
        @SerializedName("id")
        val id: Int,
        @SerializedName("namePatientTest")
        val namePatientTest: String,
        @SerializedName("patient")
        val patient: Patient,
        @SerializedName("price")
        val price: Int,
        @SerializedName("qrCode")
        val qrCode: String,
        @SerializedName("statusBook")
        val statusBook: String,
        @SerializedName("statusHealth")
        val statusHealth: String,
        @SerializedName("timeTest")
        val timeTest: String
    ){
        data class DoctorX(
            @SerializedName("addressTest")
            val addressTest: String,
            @SerializedName("avatar")
            val avatar: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("specialty")
            val specialty: SpecialtyX
        ){
            data class SpecialtyX(
                @SerializedName("image")
                val image: String,
                @SerializedName("name")
                val name: String
            )
        }
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
    }
}