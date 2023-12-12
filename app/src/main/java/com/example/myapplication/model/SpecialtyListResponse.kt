package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class SpecialtyListResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String
){
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("deleteAt")
        val deleteAt: Any,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("updatedAt")
        val updatedAt: String
    )
}