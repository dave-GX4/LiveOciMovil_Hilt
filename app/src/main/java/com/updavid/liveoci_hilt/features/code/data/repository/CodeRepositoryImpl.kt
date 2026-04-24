package com.updavid.liveoci_hilt.features.code.data.repository

import android.util.Log
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.api.CodeLiveOciApi
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.mappers.toDomain
import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser
import com.updavid.liveoci_hilt.features.code.domain.repository.CodeRepository
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val api: CodeLiveOciApi,
) : CodeRepository {

    override suspend fun getCodeOfUser(id: String): Code {
        return try {
            val response = api.getCodeFriendOfUser(id)
            response.toDomain()
        } catch (e: IOException) {
            // Error de red (sin internet, timeout, etc.)
            throw Exception("No hay conexión a internet")
        } catch (e: HttpException) {
            // Errores HTTP (400, 404, 500, etc.)
            val errorMsg = "Error del servidor: ${e.code()}"
            throw Exception(errorMsg)
        } catch (e: Exception) {
            // Errores generales (parsing, nulls, etc.)
            throw Exception("Ocurrió un error inesperado")
        }
    }

    override suspend fun searchUserByCode(id: String, code: String): FoundUser {
        return try {
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