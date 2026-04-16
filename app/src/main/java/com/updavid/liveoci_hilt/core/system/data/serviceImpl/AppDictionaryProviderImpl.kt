package com.updavid.liveoci_hilt.core.system.data.serviceImpl

import android.content.Context
import com.updavid.liveoci_hilt.core.system.domain.entity.DictionaryAppInfo
import com.updavid.liveoci_hilt.core.system.domain.service.AppDictionaryProvider
import kotlinx.serialization.json.Json
import java.io.FileNotFoundException

class AppDictionaryProviderImpl(
    private val context: Context
) : AppDictionaryProvider {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getDictionary(): Result<Map<String, DictionaryAppInfo>> {
        return try {
            // Abrimos el archivo desde la carpeta assets
            val inputStream = context.assets.open("leisure_apps_dictionary.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val jsonString = String(buffer, Charsets.UTF_8)

            // Convertimos el JSON a un Map de Kotlin
            val dictionary = json.decodeFromString<Map<String, DictionaryAppInfo>>(jsonString)

            Result.success(dictionary)

        } catch (e: FileNotFoundException) {
            // Protección: El archivo no existe
            Result.failure(Exception("Error de seguridad: Archivo de configuración no encontrado."))
        } catch (e: Exception) {
            // Protección: El JSON está mal formado o hubo otro error
            Result.failure(Exception("Error al obtener datos para analizar tu bienestar."))
        }
    }
}