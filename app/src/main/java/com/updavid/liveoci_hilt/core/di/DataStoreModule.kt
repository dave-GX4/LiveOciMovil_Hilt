package com.updavid.liveoci_hilt.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.updavid.liveoci_hilt.core.datastore.DataStoreManagerImpl
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "liveoci_prefs_v2")
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    @Provides
    @Singleton
    fun provideDataStoreService(dataStore: DataStore<Preferences>): DataStoreService {
        return DataStoreManagerImpl(dataStore)
    }
}