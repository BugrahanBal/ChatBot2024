package com.balbugrahan.chatbot2024.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.balbugrahan.chatbot2024.R
import dagger.hilt.android.qualifiers.ApplicationContext
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private lateinit var webSocketClient: WebSocketClient
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private var isConnected = false // Bağlantı durumunu takip etmek için

    fun connectWebSocket() {
        val uri = URI("wss://echo.websocket.org")
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("WebSocket", context.getString(R.string.websocket_connected))
                isConnected = true
            }

            override fun onMessage(message: String?) {
                message?.let {
                    _messageLiveData.postValue(it)
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d("WebSocket", context.getString(R.string.websocket_disconnected) + ": $reason")
                isConnected = false
                handleDisconnected()
            }

            override fun onError(ex: Exception?) {
                Log.e("WebSocket", context.getString(R.string.websocket_error) + ": ${ex?.message}")
                isConnected = false
                handleDisconnected()
            }
        }
        webSocketClient.connect()
    }

    fun sendAction(action: String) {
        if (isConnected) {
            webSocketClient.send(action) // Bağlantı varsa aksiyon çalışsın.Yoksa crash olur.
        } else {
            Log.d("WebSocket", context.getString(R.string.websocket_not_action))
            handleDisconnected()
        }
    }

    private fun handleDisconnected() {
        // Bağlantı kesilince alınacak aksiyonlar
        Log.d("WebSocket", context.getString(R.string.websocket_connection_failed))
        //reconnectWebSocket()
    }

    // Yeniden bağlanmayı başlatabiliriz.
    fun reconnectWebSocket() {
        Log.d("WebSocket", context.getString(R.string.websocket_reconnecting))
        connectWebSocket()
    }

    fun disconnectWebSocket() {
        if (isConnected) {
            try {
                webSocketClient.close()
                Log.d("WebSocket", context.getString(R.string.websocket_closed_by_user))
                isConnected = false
            } catch (e: Exception) {
                Log.e("WebSocket", context.getString(R.string.websocket_error_when_closed) + ": ${e.message}")
            }
        } else {
            Log.d("WebSocket", context.getString(R.string.websocket_already_closed))
        }
    }
}
