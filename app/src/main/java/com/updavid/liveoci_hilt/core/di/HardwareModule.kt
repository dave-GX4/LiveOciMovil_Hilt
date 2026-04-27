package com.updavid.liveoci_hilt.core.di

import com.updavid.liveoci_hilt.core.hardware.data.AndroidBiometricManager
import com.updavid.liveoci_hilt.core.hardware.data.AndroidImageStorageManager
import com.updavid.liveoci_hilt.core.hardware.data.AndroidVibrateManager
import com.updavid.liveoci_hilt.core.hardware.domian.AppBiometricManager
import com.updavid.liveoci_hilt.core.hardware.domian.ImageStorageManager
import com.updavid.liveoci_hilt.core.hardware.domian.VibrateManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareModule {
    @Binds
    @Singleton
    abstract fun bindVibrateManager(
        androidVibrateManager: AndroidVibrateManager
    ): VibrateManager

    @Binds
    @Singleton
    abstract fun bindImageStorageManager(
        androidImageStorageManager: AndroidImageStorageManager
    ): ImageStorageManager

    @Binds
    @Singleton
    abstract fun bindAppBiometricManager(
        androidBiometricManager: AndroidBiometricManager
    ): AppBiometricManager
}