package com.updavid.liveoci_hilt.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            // Le enseñamos a Gson cómo convertir el texto en LocalDateTime
            .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
                val dateString = json.asString
                try {
                    // Primero intentamos el formato ISO de Node.js (con la 'T' en medio)
                    LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
                } catch (e: Exception) {
                    // Si falla, usamos tu formato exacto de la base de datos (con espacio)
                    LocalDateTime.parse(
                        dateString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    )
                }
            })
            .create()
    }

    @Provides
    @Singleton
    @StreamingClient
    fun provideStreamingOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @LiveOciRetrofit
    fun provideLiveOciRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-live-oci-production.up.railway.app/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}