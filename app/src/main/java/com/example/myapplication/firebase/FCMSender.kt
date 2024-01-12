package com.example.myapplication.firebase

class FCMSender(private val to: String, data: Data) {
    private val data: Data

    init {
        this.data = data
    }
}