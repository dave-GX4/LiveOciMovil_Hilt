package com.updavid.liveoci_hilt.features.auth.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.api.AuthLiveOciApi
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.request.AuthSingIn
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.request.AuthSingUp
import com.updavid.liveoci_hilt.features.auth.domain.entity.AuthMessage
import com.updavid.liveoci_hilt.features.auth.domain.repository.AuthRepositry
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthLiveOciApi,
    private val dataStoreService: DataStoreService
): AuthRepositry {
    override suspend fun login(email: String, password: String): AuthMessage {
        return try {
            val requestBody = AuthSingIn(
                email,
                password
            )
            val responseDto = api.postSingIn(requestBody)

            Log.d("API_RESPONSE", responseDto.toString())

            val userId = responseDto.data
            if (userId.isBlank()) {
                throw Exception("El servidor no devolvió un ID de usuario válido.")
            }

            dataStoreService.saveUserId(responseDto.data)

            responseDto.toDomain()

        } catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("AuthRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("AuthRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("AuthRepository", "Error inesperado: ${e.message}", e)
            throw e
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthMessage {
        return try {
            val requestBody = AuthSingUp(
                name,
                email,
                password
            )
            val responseDto = api.postSingUp(requestBody)

            Log.d("API_RESPONSE", responseDto.toString())

            responseDto.toDomain()

        }catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("AuthRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("AuthRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }
}