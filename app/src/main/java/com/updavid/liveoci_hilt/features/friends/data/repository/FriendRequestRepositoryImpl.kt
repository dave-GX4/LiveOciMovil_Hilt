package com.updavid.liveoci_hilt.features.friends.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.api.FriendRequestLiveOciApi
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.CancelRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.ResponseRequestDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.request.SendRequestDto
import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRequestRepository
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FriendRequestRepositoryImpl @Inject constructor(
    private val api: FriendRequestLiveOciApi,
    private val datastore: DataStoreService
): FriendRequestRepository{
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

    override suspend fun getFriendRequest(): MessageFriend {
        TODO("Not yet implemented")
    }
}