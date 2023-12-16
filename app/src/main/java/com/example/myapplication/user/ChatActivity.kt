package com.example.myapplication.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class ChatActivity : AppCompatActivity() {

    private val webSocketExample = WebServiceClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webSocketExample.connectWebSocket()
        webSocketExample.sendMessage("Hello, WebSocket!")
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketExample.disconnectWebSocket()
    }
}