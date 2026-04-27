package com.updavid.liveoci_hilt.core.sse

import android.util.Log
import com.google.gson.Gson
import com.updavid.liveoci_hilt.core.di.StreamingClient
import com.updavid.liveoci_hilt.core.sse.dtos.CodeUpdateEventDto
import com.updavid.liveoci_hilt.core.sse.dtos.FriendListUpdateEventDto
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources

class SseDataSourceImpl @Inject constructor(
    @StreamingClient private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : SseDataSource {

    override fun streamCode(id: String): Flow<CodeUpdateEventDto> = callbackFlow {
        val request = Request.Builder()
            .url("https://api-live-oci-production.up.railway.app/api/v2/sse/stream/$id")
            .header("Accept", "text/event-stream")
            .build()

        val listener = object : EventSourceListener() {
            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                try {
                    // Solo parseamos si el evento es una actualización de código
                    if (type == "CODE_UPDATED") {
                        val dto = gson.fromJson(data, CodeUpdateEventDto::class.java)
                        trySend(dto)
                    }
                } catch (e: Exception) {
                    Log.e("GlobalSSE", "Error parseando evento $type: ${e.message}")
                }
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                close(t ?: Exception("Conexión global SSE interrumpida"))
            }
        }

        val eventSource = EventSources.createFactory(okHttpClient).newEventSource(request, listener)

        awaitClose {
            Log.d("GlobalSSE", "Cerrando conexión SSE para liberar recursos")
            eventSource.cancel()
        }
    }

    override fun streamFriendUpdates(id: String): Flow<FriendListUpdateEventDto> = callbackFlow {
        val request = Request.Builder()
            .url("https://api-live-oci-production.up.railway.app/api/v2/sse/stream/$id")
            .header("Accept", "text/event-stream")
            .build()

        val listener = object : EventSourceListener() {
            override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
                try {
                    // Escuchamos el evento específico de la lista de amigos
                    if (type == "FRIEND_LIST_UPDATED") {
                        val dto = gson.fromJson(data, FriendListUpdateEventDto::class.java)
                        trySend(dto)
                    }
                } catch (e: Exception) {
                    Log.e("SseFriends", "Error parseando evento $type: ${e.message}")
                }
            }

            override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
                close(t ?: Exception("Conexión SSE para amigos interrumpida"))
            }
        }

        val eventSource = EventSources.createFactory(okHttpClient).newEventSource(request, listener)

        awaitClose {
            Log.d("SseFriends", "Cerrando conexión SSE de amigos")
            eventSource.cancel()
        }
    }
}