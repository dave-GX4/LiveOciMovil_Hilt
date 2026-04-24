package com.updavid.liveoci_hilt.features.code.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.core.ssedatasource.SseDataSource
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.api.CodeLiveOciApi
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.mappers.toDomain
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
        // Dentro de flow { }, SÍ se puede usar suspend (como first())
        val userId = dataStore.getUserId().first()
            ?: throw Exception("Sesión no válida: Usuario no encontrado")

        // Obtencion de Flow del DataSource y emitimos todos sus valores
        val sseFlow = sseDataSource.streamCode(userId).map { it.toDomain() }

        emitAll(sseFlow) // Conecta la tubería del SSE con la salida de esta función

    }.catch { e ->
        Log.e("CodeRepository", "Error en el stream: ${e.message}")
        throw Exception("Se perdió la conexión en tiempo real: ${e.message}")
    }

    override suspend fun searchUserByCode(code: String): FoundUser {
        return try {
            val id = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida: Usuario no encontrado")

            val response = api.searchUserByCode(id, code)
            response.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
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