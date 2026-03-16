package com.updavid.liveoci_hilt.core.di

import android.content.Context
import androidx.room.Room
import com.updavid.liveoci_hilt.core.database.AppDataBase
import com.updavid.liveoci_hilt.core.database.dao.ScheduleDao
import com.updavid.liveoci_hilt.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase{
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "LiveOciDB"
        ).build()
    }

    @Provides
    fun provideScheduleDao(db: AppDataBase): ScheduleDao = db.scheduleDao()

    @Provides
    fun provideUserDao(db: AppDataBase): UserDao = db.userDao()
}