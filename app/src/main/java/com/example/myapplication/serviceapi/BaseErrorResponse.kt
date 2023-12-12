package com.example.myapplication.serviceapi

import com.google.gson.annotations.SerializedName

data class BaseErrorResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)
