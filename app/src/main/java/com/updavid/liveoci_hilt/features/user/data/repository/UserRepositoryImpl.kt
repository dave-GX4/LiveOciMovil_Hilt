package com.updavid.liveoci_hilt.features.user.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.database.dao.UserDao
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.user.data.datasource.local.toDomain
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.api.UserLiveOciApi
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper.toEntity
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.request.UserRequestDto
import com.updavid.liveoci_hilt.features.user.domain.entity.User
import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserLiveOciApi,
    private val dao: UserDao,
    private val dataStore: DataStoreService
): UserRepository {
    override suspend fun deleteAccountUser(): Message {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val responseDto = api.deleteAccount(userId)

            if (responseDto.status) {
                dao.deleteUser()
                dataStore.clearSession()
            } else {
                throw Exception(responseDto.message ?: "No se pudo confirmar la eliminación.")
            }

            responseDto.toDomain()

        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("UserRepository", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("UserRepository", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }

    override suspend fun getUserByIdRemote() {
        try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val remoteUser = api.getUserById(userId)
            dao.saveOrUpdateUser(remoteUser.toEntity())

        } catch (e: HttpException) {
            val code = e.code()

            if (code == 404 || code == 401) {
                logoutLocal()
                throw Exception("Tu sesión ha expirado o la cuenta ya no existe. Inicia sesión de nuevo.")
            }

            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            throw Exception("Error de conexión, revisa tu internet.")
        } catch (e: Exception) {
            Log.e("UserRepository", "Error inesperado", e)
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
                emit(null)
            }
    }

    override suspend fun updateNameUser(name: String): Message {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val body = UserRequestDto(name = name)

            val responseDto = api.updateUser(userId, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
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

    override suspend fun updateEmailUser(email: String): Message {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val body = UserRequestDto(email = email)

            val responseDto = api.updateUser(userId, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
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

    override suspend fun updatePasswordUser(password: String): Message {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val body = UserRequestDto(password = password)

            val responseDto = api.updateUser(userId, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
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
        interests: List<String>,
        topics: List<String>,
        description: String
    ): Message {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val body = UserRequestDto(
                interests = interests,
                topics = topics,
                description = description
            )

            val responseDto = api.updateUser(userId, body)

            responseDto.toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
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

    override suspend fun logoutLocal(): Result<Unit> {
        return runCatching {
            dao.deleteUser()
            dataStore.clearSession()
        }
    }
}