package com.example.myapplication.user

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
class ChatWebSocketClient(serverUri: URI, private val messageListener: (String) -> Unit) : WebSocketClient(serverUri) {

    override fun connect() {
        super.connect()
    }


    override fun onOpen(handshakedata: ServerHandshake?) {
        // When WebSocket connection opened
        println(this.uri.getHost().toString())
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        // When WebSocket connection closed
    }

    override fun onMessage(message: String?) {
        // When Receive a message
        messageListener.invoke(message ?: "")
    }

    override fun onError(ex: Exception?) {
        // When An error occurred
    }

    fun sendMessage(message: String) {
        send(message)
    }
}

