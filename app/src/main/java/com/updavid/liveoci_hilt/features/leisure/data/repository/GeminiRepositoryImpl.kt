package com.updavid.liveoci_hilt.features.leisure.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.database.dao.UserDao
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.api.GeminiLiveOciApi
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.request.ActivityTemplateRequestDto
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiActivity
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiMessage
import com.updavid.liveoci_hilt.features.leisure.domain.repository.GeminiRepository
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val api: GeminiLiveOciApi,
    private val dataStore: DataStoreService
): GeminiRepository{
    override suspend fun generateActivityTemplate(activity: BoredActivity): GeminiMessage {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("No se encontró sesión de usuario")

            val requestDto = ActivityTemplateRequestDto(
                activity = activity.activity,
                type = activity.type,
                participants = activity.participants,
                duration = activity.duration,
                kidFriendly = activity.kidFriendly
            )

            val responseDto = api.generateActivity(userId, requestDto)

            responseDto.toDomain()
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("GeminiRepository", "Error parseando JSON: $jsonException")
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            Log.e("GeminiRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun generateActivitiesOptions(): List<GeminiActivity> {
        return try {
            val responseDto = api.generateActivities()

            responseDto.map { it.toDomain() }
        }catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("GeminiRepository", "Error parseando JSON: $jsonException")
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            Log.e("GeminiRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }catch (e: Exception) {
            Log.e("Geminiepository", "Error inesperado: ${e.message}", e)
            throw e
        }
    }
}