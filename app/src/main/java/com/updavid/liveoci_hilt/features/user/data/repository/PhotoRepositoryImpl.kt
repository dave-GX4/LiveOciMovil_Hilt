package com.updavid.liveoci_hilt.features.user.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.api.PhotoLiveOciApi
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PhotoLiveOciApi,
    private val dataStore: DataStoreService
): PhotoRepository {

    override suspend fun getPhotoUserRemote() {
        try {
            val userId = dataStore.getUserId().first() ?: throw Exception("Sesión no válida")
            val responseDto = api.getPhotoUser(userId)
            val photo = responseDto.toDomain()

            val currentLocalUrl = dataStore.getUserPhotoUrl().first()

            if (currentLocalUrl != photo.url) {
                Log.d("AppDebug", "Repository: La URL cambió, actualizando DataStore...")
                dataStore.saveUserPhotoUrl(photo.url)
            } else {
                Log.d("AppDebug", "Repository: La URL es idéntica, no se actualiza para evitar parpadeos.")
            }

        } catch (e: Exception) {
            Log.e("AppDebug", "Repository: Error obteniendo foto remota: ${e.message}")
            throw Exception("Ocurrió un error: ${e.message}")
        }
    }

    override suspend fun saveOrUpdatePhotoUserRemote(imageFile: File): Message {
        return try {
            val userId = dataStore.getUserId().first()
                ?: throw Exception("Sesión no válida")

            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())

            val multipartBody = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

            val responseDto = api.saveOrUpdatePhotoUser(userId, multipartBody)

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
            Log.e("PhotoRepository", "Error de red: ${e.message}")
            throw Exception("Error de conexión, revisa tu internet.")

        } catch (e: Exception) {
            Log.e("PhotoRepository", "Error interno en el móvil: ${e.message}", e)
            throw Exception("Ocurrió un error interno al procesar la solicitud.")
        }
    }
}