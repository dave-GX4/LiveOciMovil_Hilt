package com.updavid.liveoci_hilt.features.user.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.database.dao.UserDao
import com.updavid.liveoci_hilt.features.user.data.datasource.local.toDomain
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.api.UserLiveOciApi
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper.toEntity
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.request.UserRequestDto
import com.updavid.liveoci_hilt.features.user.domain.entity.User
import com.updavid.liveoci_hilt.features.user.domain.entity.UserMessage
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserLiveOciApi,
    private val dao: UserDao
): UserRepository {
    override suspend fun deleteAccountUser(id: String): UserMessage {
        return try {
            val responseDto = api.deleteAccount(id)

            responseDto.toDomain()
        }catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("UserRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun getUserByIdRemote(id: String) {
        try {
            val remoteUser = api.getUserById(id)

            dao.saveOrUpdateUser(
                remoteUser.toEntity()
            )
        }catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("UserRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        } catch (e: Exception) {
            Log.e("UserRepository", "Error inesperado: ${e.message}", e)
            throw e
        }
    }

    override fun getUserRoom(): Flow<User?> {
        return dao.getUser()
            .map { entity ->
                entity?.toDomain()
            }
            .catch { e ->
                Log.e("DatabaseError", "Error al leer el usuario de Room", e)
                throw Exception("No pudimos cargar la información de tu cuenta localmente.")
            }
    }

    override suspend fun updateNameUser(id: String, name: String): UserMessage {
        return try {
            val body = UserRequestDto(name = name)

            val responseDto = api.updateUser(id, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("UserRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun updateEmailUser(id: String, email: String): UserMessage {
        return try {
            val body = UserRequestDto(email = email)

            val responseDto = api.updateUser(id, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("UserRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun updatePasswordUser(id: String, password: String): UserMessage {
        return try {
            val body = UserRequestDto(password = password)

            val responseDto = api.updateUser(id, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("UserRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun updateTastesUser(
        id: String,
        interests: List<String>,
        topics: List<String>,
        description: String
    ): UserMessage {
        return try {
            val body = UserRequestDto(
                interests = interests,
                topics = topics,
                description = description
            )

            val responseDto = api.updateUser(id, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("UserRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }
}