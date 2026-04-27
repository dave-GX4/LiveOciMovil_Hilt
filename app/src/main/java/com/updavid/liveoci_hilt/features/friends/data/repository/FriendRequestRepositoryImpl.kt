package com.updavid.liveoci_hilt.features.friends.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.core.sse.SseDataSource
import com.updavid.liveoci_hilt.core.sse.dtos.FriendListUpdateEventDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.api.FriendRequestLiveOciApi
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.CancelRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.RemoveFriendRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.ResponseRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.SendRequestDto
import com.updavid.liveoci_hilt.features.friends.domain.entity.Friend
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendRequest
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendUpdateEvent
import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FriendRequestRepositoryImpl @Inject constructor(
    private val api: FriendRequestLiveOciApi,
    private val datastore: DataStoreService,
    private val sseDataSource: SseDataSource
): FriendRepository{
    override suspend fun cancelFriendRequest(id: String): MessageFriend {
        return try {
            val currentUserId = datastore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val request = CancelRequestDto(userId = currentUserId)
            val response = api.cancelRequest(id, request)

            response.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("FriendRequestRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("FriendRequestRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun responseFriendRequest(
        id: String,
        status: String
    ): MessageFriend {
        return try {
            val currentUserId = datastore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val request = ResponseRequestDto(status = status, userId = currentUserId)
            val response = api.responseRequest(id, request)

            response.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("FriendRequestRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("FriendRequestRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun sendFriendRequest(userIdB: String): MessageFriend {
        return try {
            val currentUserId = datastore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val request = SendRequestDto(userIdA = currentUserId, userIdB = userIdB)
            val response = api.sendRequest(request)

            response.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val json = JSONObject(errorJsonString)
                if (json.has("error")) {
                    json.getString("error")
                } else if (json.has("message")) {
                    json.getString("message")
                } else {
                    "Error inesperado del servidor."
                }
            } catch (jsonException: Exception) {
                Log.e("FriendRequestRepository", "El backend respondió esto y no es JSON válido: $errorJsonString")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("FriendRequestRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun getFriendRequest(): List<FriendRequest> {
        return try {
            val currentUserId = datastore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val response = api.getPendingRequest(currentUserId)

            response.map { it.toDomain() }

        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("FriendRequestRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("FriendRequestRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun getAllFriends(): List<Friend> {
        return try {
            val currentUserId = datastore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val response = api.getAllFriends(currentUserId)
            response.map { it.toDomain() }

        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val json = JSONObject(errorJsonString)
                if (json.has("error")) json.getString("error") else json.getString("message")
            } catch (ex: Exception) { "Error desconocido del servidor." }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun removeFriend(id: String): MessageFriend {
        return try {
            val userId = datastore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val request = RemoveFriendRequestDto(userId)

            val response = api.removeFriend(id, request)

            response.toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                val json = JSONObject(errorJsonString)
                if (json.has("error")) json.getString("error") else json.getString("message")
            } catch (ex: Exception) { "Error desconocido del servidor." }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override fun streamFriendUpdates(): Flow<FriendUpdateEvent> = flow {
        val userId = datastore.getUserId().first()
            ?: throw Exception("Sesión no válida")

        val sseFlow = sseDataSource.streamFriendUpdates(userId).map { it.toDomain() }

        emitAll(sseFlow)
    }.catch { e ->
        Log.e("FriendRepository", "Error en el stream de amigos: ${e.message}")
        throw Exception(e.message ?: "Se perdió la conexión en tiempo real")
    }
}