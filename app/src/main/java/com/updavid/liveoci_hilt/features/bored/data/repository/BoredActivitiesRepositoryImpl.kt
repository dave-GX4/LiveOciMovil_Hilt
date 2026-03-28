package com.updavid.liveoci_hilt.features.bored.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.features.bored.data.datasource.remote.api.ActivitiesBoredApi
import com.updavid.liveoci_hilt.features.bored.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.bored.domain.repository.BoredActivitiesRepository
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BoredActivitiesRepositoryImpl @Inject constructor(
    private val api: ActivitiesBoredApi
): BoredActivitiesRepository {

    override suspend fun getRandomActivity(): BoredActivity {
        return try {
            val responseDto = api.getRandomActivity()
            responseDto.toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ActivitiesRepository", "Error parseando JSON: $jsonException")
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            Log.e("ActivitiesRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun getFilterActivities(type: String?, participants: Int?): List<BoredActivity> {
        return try {
            val responseDto = api.getFilterActivities(type = type, participants = participants)

            responseDto.map {
                it.toDomain()
            }
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ActivitiesRepository", "Error parseando JSON: $jsonException")
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            Log.e("ActivitiesRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }

    override suspend fun getActivityByKey(key: String): BoredActivity {
        return try {
            // LE PASAMOS EL KEY A LA API
            val responseDto = api.getActivityByKey(key = key)
            responseDto.toDomain()
        } catch (e: HttpException) {
            val errorJsonString = e.response()?.errorBody()?.string()
            val errorMessage = try {
                JSONObject(errorJsonString).getString("message")
            } catch (jsonException: Exception) {
                Log.e("ActivitiesRepository", "Error parseando JSON: $jsonException")
                "Error desconocido del servidor."
            }
            throw Exception(errorMessage)
        } catch (e: IOException) {
            Log.e("ActivitiesRepository", "Error de red: ${e.message}", e)
            throw Exception("Error de conexión, revisa tu internet.")
        }
    }


}