package com.updavid.liveoci_hilt.core.ssedatasource

import android.util.Log
import com.google.gson.Gson
import com.updavid.liveoci_hilt.core.di.StreamingClient
import com.updavid.liveoci_hilt.core.ssedatasource.dtos.CodeSSEDto
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response.CodeResponseDto
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

    override fun streamCode(id: String): Flow<CodeSSEDto> = callbackFlow {
        val request = Request.Builder()
            .url("https://api-live-oci-production.up.railway.app/api/v2/code/stream/$id")
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
                    val dto = gson.fromJson(data, CodeSSEDto::class.java)
                    trySend(dto)
                } catch (e: Exception) {
                    Log.e("SSE", "Error: ${e.message}")
                }
            }
            override fun onFailure(
                eventSource: EventSource,
                t: Throwable?,
                response: Response?
            ) {
                close(t ?: Exception("Error en SSE"))
            }
        }

        val eventSource = EventSources.createFactory(okHttpClient).newEventSource(
            request,
            listener
        )
        awaitClose {
            eventSource.cancel()
        }
    }
}