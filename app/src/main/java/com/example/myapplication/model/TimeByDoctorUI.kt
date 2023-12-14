package com.example.myapplication.model

data class TimeByDoctorUI(
    val id: Int,
    val value: String,
    var isClicked: Boolean,
    val isBooked: Boolean,
    val price: Int
)