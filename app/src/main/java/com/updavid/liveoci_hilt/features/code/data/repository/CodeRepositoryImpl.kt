package com.updavid.liveoci_hilt.features.code.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.core.sse.SseDataSource
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.api.CodeLiveOciApi
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.mappers.toDomain
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.request.SearchByCodeRequest
import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser
import com.updavid.liveoci_hilt.features.code.domain.repository.CodeRepository
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

class CodeRepositoryImpl @Inject constructor(
    private val api: CodeLiveOciApi,
    private val sseDataSource: SseDataSource,
    private val dataStore: DataStoreService
) : CodeRepository {
    override fun streamCodeOfUser(): Flow<Code> = flow {
        val userId = dataStore.getUserId().first()
            ?: throw Exception("Sesión no válida: Usuario no encontrado")

        var currentRecordId = ""

        try {
            val initialDto = api.getCodeOfUser(userId)
            currentRecordId = initialDto.id
            emit(initialDto.toDomain())
        } catch (e: Exception) {
            Log.e("CodeRepository", "Error REAL al cargar código inicial: ${e.message}", e)
            throw Exception("Fallo al procesar los datos: ${e.message}")
        }

        val sseFlow = sseDataSource.streamCode(userId).map { sseDto ->
            sseDto.toDomain(
                recordId = currentRecordId,
                currentUserId = userId
            )
        }

        emitAll(sseFlow)

    }.catch { e ->
        Log.e("CodeRepository", "Error en el stream: ${e.message}")
        throw Exception(e.message ?: "Se perdió la conexión en tiempo real")
    }

    override suspend fun searchUserByCode(code: String): FoundUser {
        return try {
            val id = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val request = SearchByCodeRequest(code)
            val response = api.searchUserByCode(id, request)
            response.toDomain()
        } catch (e: HttpException) {
            val errorBodyString = e.response()?.errorBody()?.string()
            val errorMessage = if (!errorBodyString.isNullOrBlank()) {
                try {
                    val json = JSONObject(errorBodyString)
                    if (json.has("error")) {
                        json.getString("error")
                    }
                    else if (json.has("message")) {
                        json.getString("message")
                    }
                    else {
                        "Error inesperado del servidor."
                    }
                } catch (jsonException: Exception) {
                    "Error de conexión."
                }
            } else {
                "Error ${e.code()}"
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("CodeRepository", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")
        } catch (e: Exception) {
            Log.e("CodeRepository", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }
}