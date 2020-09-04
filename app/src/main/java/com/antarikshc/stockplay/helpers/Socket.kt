package com.antarikshc.stockplay.helpers

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.*


/**
 * Wrapper for OkHttp WebSockets
 * Supports Coroutines Flow
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class Socket(private val client: OkHttpClient) {

    companion object {
        private val TAG = Socket::class.java.simpleName
    }

    fun connect(url: String) = callbackFlow<String> {
        val request = Request.Builder().url(url).build()

        val webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "Connected: $response")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // Emit value to Flow
                offer(text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d(TAG, "Closed")
            }
        })

        // Wait for the Flow to finish
        awaitClose { webSocket.close(1000, "Closed") }
    }

}