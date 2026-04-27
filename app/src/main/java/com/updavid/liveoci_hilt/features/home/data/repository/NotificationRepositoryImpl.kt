package com.updavid.liveoci_hilt.features.home.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.core.sse.SseDataSource
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.api.NotificationLiveOciApi
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.home.domain.entity.Notification
import com.updavid.liveoci_hilt.features.home.domain.entity.NotificationMessage
import com.updavid.liveoci_hilt.features.home.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationLiveOciApi,
    private val dataStore: DataStoreService,
    private val sseDataSource: SseDataSource
): NotificationRepository {
    override suspend fun getNotifications(
        limit: Int
    ): List<Notification> {
        return try {
            val userId = dataStore.getUserId().first()?: throw Exception("Sesión no válida")
            val response = api.getNotifications(userId, limit)
            response.map { it.toDomain() }
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonObject = JSONObject(errorJsonString ?: "")
                if (jsonObject.has("error")) {
                    jsonObject.getString("error")
                } else {
                    "Error desconocido del servidor."
                }
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("NotificationRepo", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("NotificationRepo", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }

    override suspend fun markAllNotificationsRead(): NotificationMessage {
        return try {
            val userId = dataStore.getUserId().first() ?: throw Exception("Sesión no válida")
            api.markAllNotificationsRead(userId).toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonObject = JSONObject(errorJsonString ?: "")
                if (jsonObject.has("error")) {
                    jsonObject.getString("error")
                } else {
                    "Error desconocido del servidor."
                }
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("NotificationRepo", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("NotificationRepo", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }

    override suspend fun markNotificationRead(notificationId: String): NotificationMessage {
        return try {
            api.markNotificationRead(notificationId).toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val jsonObject = JSONObject(errorJsonString ?: "")
                if (jsonObject.has("error")) {
                    jsonObject.getString("error")
                } else {
                    "Error desconocido del servidor."
                }
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("NotificationRepo", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("NotificationRepo", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }

    override fun streamNotifications(): Flow<Notification> {
        return dataStore.getUserId().filterNotNull().flatMapLatest { userId ->
            sseDataSource.streamNotifications(userId).map { it.toDomain() }
        }
    }
}