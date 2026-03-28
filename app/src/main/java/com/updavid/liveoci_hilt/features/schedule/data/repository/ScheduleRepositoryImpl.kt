package com.updavid.liveoci_hilt.features.schedule.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.database.dao.ScheduleDao
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
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val api: ScheduleLiveOciApi,
    private val dao: ScheduleDao
): ScheduleRepository {
    override suspend fun addSchedule(
        title: String,
        days: List<Int>,
        start_time: String,
        end_time: String,
        active: Boolean,
        type: String
    ): ScheduleMessage {
        return try {
            val requestBody = ScheduleRequestDto(
                title,
                days,
                start_time,
                end_time,
                active,
                type
            )

            val responseDto = api.addSchedule(requestBody)

            Log.d("API_RESPONSE", responseDto.toString())

            responseDto.toDomain()
        }catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
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
        start_time: String,
        end_time: String,
        active: Boolean
    ): ScheduleMessage {
        return try {
            val requestBody = ScheduleUpdateRequestDto(
                title,
                days,
                start_time,
                end_time,
                active
            )

            val responseDto = api.updateSchedule(id, requestBody)

            Log.d("API_RESPONSE", responseDto.toString())

            responseDto.toDomain()
        }catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
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
                Log.e("DatabaseError", "Error al leer horarios de Room", e)

                throw Exception("No pudimos cargar tus horarios. Intenta reiniciar la app.")
            }
    }

    override suspend fun getAllSchedulesRemote() {
        try {
            val remoteSchedule = api.getAllSchedules()

            dao.refreshSchedules(
                remoteSchedule.map {
                    it.toEntity()
                }
            )
        }catch (e: HttpException) {
            // Cachar errores del bakend (401, 402, 500, etc.)
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                // Extraemos el campo "message" del JSON de error
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