package com.antarikshc.stockplay.helpers

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.retryWhen
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Wrapper for OkHttp WebSockets
 * Supports Coroutines Flow
 */
@Suppress("EXPERIMENTAL_API_USAGE")
@Singleton
class Socket @Inject constructor(private val client: OkHttpClient) {

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
                if (code != 1000) close(SocketNetworkException("Network Failure"))
                Log.d(TAG, "Closed #$code")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                close(SocketNetworkException("Network Failure"))
                Log.d(TAG, "Network Failure")
            }
        })

        // Wait for the Flow to finish
        awaitClose { webSocket.close(1000, "Closed") }
    }
        .retryWhen { cause, attempt ->
            // Exponential backoff of 1 second on each retry
            if (attempt > 1) delay(1000 * attempt)
            else if (attempt >= 8) delay(8000)

            Log.d(TAG, "Retrying #$attempt")

            // Do not retry for IllegalArgument or 3 attempts are reached
            cause is SocketNetworkException
        }

    class SocketNetworkException(message: String) : Exception(message)

}