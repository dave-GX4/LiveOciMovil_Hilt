package com.updavid.liveoci_hilt.core.di

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LiveOciRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BoredRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeminiModel