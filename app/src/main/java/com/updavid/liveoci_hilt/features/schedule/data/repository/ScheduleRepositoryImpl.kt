package com.updavid.liveoci_hilt.features.schedule.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.database.dao.ScheduleDao
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.schedule.data.datasource.local.mapper.toDomain
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.api.ScheduleLiveOciApi
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.mappers.toDomain
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.mappers.toEntity
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request.ScheduleRequestDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request.ScheduleUpdateRequestDto
import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule
import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val api: ScheduleLiveOciApi,
    private val dao: ScheduleDao,
    private val dataStoreService: DataStoreService
): ScheduleRepository {
    override suspend fun addSchedule(
        title: String,
        days: List<Int>,
        startTime: String,
        endTime: String,
        active: Boolean,
        type: String
    ): ScheduleMessage {
        return try {
            val userId = dataStoreService.getUserId().firstOrNull()
                ?: throw Exception("Sesión no válida")

            val requestBody = ScheduleRequestDto(
                title,
                days,
                startTime,
                endTime,
                active,
                type
            )

            val responseDto = api.addSchedule(userId, requestBody)

            Log.d("API_RESPONSE", responseDto.toString())
            getAllSchedulesRemote()

            responseDto.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ScheduleRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("ScheduleRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun updateSchedule(
        id: String,
        title: String,
        days: List<Int>,
        startTime: String,
        endTime: String,
        active: Boolean
    ): ScheduleMessage {
        return try {
            val requestBody = ScheduleUpdateRequestDto(
                title,
                days,
                startTime,
                endTime,
                active
            )

            val responseDto = api.updateSchedule(id, requestBody)

            Log.d("API_RESPONSE", responseDto.toString())
            getAllSchedulesRemote()

            responseDto.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ScheduleRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("ScheduleRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun deleteSchedule(id: String): ScheduleMessage {
        return try {
            val responseDto = api.deleteSchedule(id)
            getAllSchedulesRemote()

            responseDto.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ScheduleRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("ScheduleRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override fun getAllSchedulesRoom(): Flow<List<Schedule>> {
        return dao.getAllSchedules()
            .map { entities ->
                entities.map { it.toDomain() }
            }
            .catch { e ->
                Log.e("DatabaseError", "Error al leer todos los horarios de Room", e)
                throw Exception("No pudimos cargar tus horarios. Intenta reiniciar la app.")
            }
    }

    override fun getScheduleByIdRoom(id: String): Flow<Schedule?> {
        return dao.getScheduleById(id)
            .map { entity ->
                entity?.toDomain()
            }
            .catch { e ->
                Log.e("DatabaseError", "Error al leer el horario por ID en Room", e)
                throw Exception("Ocurrió un error al cargar el detalle del horario.")
            }
    }

    override fun getScheduleByTypeRoom(type: String): Flow<Schedule?> {
        return dao.getScheduleByType(type)
            .map { entity ->
                entity?.toDomain()
            }
            .catch { e ->
                Log.e("DatabaseError", "Error al leer el horario por tipo en Room", e)
                throw Exception("Error al verificar los horarios existentes.")
            }
    }

    override suspend fun getAllSchedulesRemote() {
        try {
            val userId = dataStoreService.getUserId().firstOrNull()
                ?: throw Exception("Sesión no válida")

            val remoteSchedule = api.getAllSchedules(userId)

            dao.refreshSchedules(
                remoteSchedule.map { it.toEntity() }
            )
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ScheduleRepository", "Error parseando JSON de error: $jsonException")
                "Error desconocido del servidor."
            }

            throw Exception(errorMessage)

        } catch (e: IOException) {
            Log.e("ScheduleRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        } catch (e: Exception) {
            Log.e("ScheduleRepository", "Error inesperado: ${e.message}", e)
            throw e
        }
    }
}