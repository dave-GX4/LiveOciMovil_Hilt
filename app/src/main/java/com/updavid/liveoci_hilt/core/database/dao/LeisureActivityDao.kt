package com.updavid.liveoci_hilt.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.updavid.liveoci_hilt.core.database.entities.ActivityEntity
import com.updavid.liveoci_hilt.core.database.entities.LeisureRecordEntity
import com.updavid.liveoci_hilt.core.database.relations.LeisureWithActivityRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface LeisureActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<ActivityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeisureRecords(records: List<LeisureRecordEntity>)

    // Inserta ambas listas en una sola transacción para mantener integridad
    @Transaction
    suspend fun insertCombinedData(activities: List<ActivityEntity>, records: List<LeisureRecordEntity>) {
        insertActivities(activities)
        insertLeisureRecords(records)
    }

    // Devuelve el Flow con los datos ya unidos
    @Transaction
    @Query("SELECT * FROM leisure_records")
    fun getAllLeisureWithActivities(): Flow<List<LeisureWithActivityRelation>>

    @Query("DELETE FROM leisure_records")
    suspend fun clearAllLeisureRecords()

    @Query("DELETE FROM activities WHERE uuid = :activityId")
    suspend fun deleteActivityById(activityId: String)

    @Query("SELECT * FROM leisure_records WHERE uuid = :id")
    suspend fun getLeisureRecordById(id: String): LeisureRecordEntity?

    // Para modificar (y hacer rollback)
    @Query("UPDATE leisure_records SET status = :status, satisfaction = :satisfaction, startTime = :startTime, endTime = :endTime WHERE uuid = :id")
    suspend fun updateLeisureRecordStatus(id: String, satisfaction: Int, status: String, startTime: String, endTime: String)
}