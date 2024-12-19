package com.balbugrahan.chatbot2024.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketRepository @Inject constructor() {

    private lateinit var webSocketClient: WebSocketClient
    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> = _messageLiveData

    private var isConnected = false // Bağlantı durumunu takip etmek için

    fun connectWebSocket() {
        val uri = URI("wss://echo.websocket.org")
        webSocketClient = object : WebSocketClient(uri) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                Log.d("WebSocket", "Bağlandı")
                isConnected = true
            }
            override fun onMessage(message: String?) {
                message?.let {
                    _messageLiveData.postValue(it)
                }
            }
            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                Log.d("WebSocket", "Kapatıldı: $reason")
                isConnected = false
                handleDisconnected()
            }
            override fun onError(ex: Exception?) {
                Log.e("WebSocket", "Hata: ${ex?.message}")
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
            Log.d("WebSocket", "WebSocket bağlantısı yok aksiyon gönderemezsin...")
            handleDisconnected() // Bağlantı yoksa aksiyon alabiliriz.
        }
    }
    private fun handleDisconnected() {
        // Bağlantı kesilince alınacak aksiyonlar
        Log.d("WebSocket", "WebSocket bağlantısı koptu...")
        // Örnek olarak yeniden bağlanmayı deneyebiliriz veya kullanıcıya bilgi verebiliriz.
        //reconnectWebSocket()
    }
    // Yeniden bağlanmayı başlatabiliriz.
    private fun reconnectWebSocket() {
        Log.d("WebSocket", "Tekrar websockete bağlanmaya çalışıyor...")
        connectWebSocket()
    }
    fun disconnectWebSocket() {
        if (isConnected) {
            try {
                webSocketClient.close()
                Log.d("WebSocket", "WebSocket kullanıcı tarafından kapatıldı.")
                isConnected = false
            } catch (e: Exception) {
                Log.e("WebSocket", "WebSocket kapatılırken hata oluştu: ${e.message}")
            }
        } else {
            Log.d("WebSocket", "WebSocket zaten kapalı.")
        }
    }
}

