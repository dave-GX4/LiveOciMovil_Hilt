package com.updavid.liveoci_hilt.core.database.dao

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.updavid.liveoci_hilt.core.database.entities.ScheduleEntity

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules")
    fun getAllSchedules(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE uuid = :id LIMIT 1")
    fun getScheduleById(id: String): Flow<ScheduleEntity?>

    @Query("SELECT * FROM schedules WHERE type = :type LIMIT 1")
    fun getScheduleByType(type: String): Flow<ScheduleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Query("DELETE FROM schedules")
    suspend fun deleteAllSchedules()

    @Transaction
    suspend fun refreshSchedules(schedules: List<ScheduleEntity>) {
        deleteAllSchedules()
        insertSchedules(schedules)
    }
}