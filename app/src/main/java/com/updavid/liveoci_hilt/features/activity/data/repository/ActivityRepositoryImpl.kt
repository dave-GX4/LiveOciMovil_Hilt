package com.updavid.liveoci_hilt.features.activity.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.database.dao.LeisureActivityDao
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.api.ActivityLiveOciApi
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.mapper.toActivityEntity
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.mapper.toLeisureEntity
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.request.ActivityRequestDto
import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val api: ActivityLiveOciApi,
    private val dao: LeisureActivityDao,
    private val dataStore: DataStoreService
): ActivityRepository {
    override suspend fun createActivity(
        name: String,
        description: String,
        type: String,
        category: String,
        durationMinutes: Int,
        socialType: String
    ): ActivityMessage {
        return try {
            val userId = dataStore.getUserId().first() ?: throw Exception("Sesión no válida")

            val request = ActivityRequestDto(
                name = name,
                description = description,
                type = type,
                category = category,
                durationMinutes = durationMinutes,
                socialType = socialType
            )

            val response = api.createActivityRemote(userId, request)

            response.toDomain()

        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                "Error desconocido del servidor al crear."
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            throw Exception("Error de conexión, revisa tu internet.")
        } catch (e: Exception) {
            throw Exception(e.message ?: "Ocurrió un error inesperado.")
        }
    }

    override suspend fun deleteActivity(): ActivityMessage {
        TODO("Not yet implemented")
    }

    override suspend fun syncActivitiesFromRemote() {
        try {
            val userId = dataStore.getUserId().first()?: throw Exception("Sesión no válida")
            val dtoList = api.getLeisureWithActivitiesRemote(userId)
            Log.d("ActivityRepository", "Respuesta exitosa del servidor: $dtoList")

            // Mapear a entidades separadas
            val activities = dtoList.map { it.toActivityEntity() }.distinctBy { it.uuid }
            val records = dtoList.map { it.toLeisureEntity() }

            dao.insertCombinedData(activities, records)

        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            Log.e("ActivityRepository", "Error HTTP del servidor (Crudo): $errorJsonString")

            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("ActivityRepository", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("ActivityRepository", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }

    override fun getActivitiesStream(): Flow<List<LeisureRecord>> {
        return dao.getAllLeisureWithActivities()
            .map { relationsList ->
                relationsList.map { it.toDomain() }
            }
            .catch { exception ->
                exception.printStackTrace()
                emit(emptyList())
            }
    }
}