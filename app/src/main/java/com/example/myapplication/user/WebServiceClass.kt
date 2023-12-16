package com.example.myapplication.user

import okhttp3.*
import okhttp3.Request

class WebServiceClass {

    private var webSocket: WebSocket? = null

    fun connectWebSocket() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("wss://your-websocket-url.com")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                // WebSocket connection opened
                println("WebSocket opened")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // Received a message from the server
                println("Received message: $text")

                // Handle the received data as needed, e.g., update UI or store in your app's data model
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // WebSocket connection closed
                println("WebSocket closed: $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // WebSocket connection failure
                println("WebSocket failure: ${t.message}")
            }
        })
    }

    fun disconnectWebSocket() {
        webSocket?.close(1000, "User disconnect")
    }

    // You can send messages to the server
    fun sendMessage(message: String) {
        webSocket?.send(message)
    }
}