package com.updavid.liveoci_hilt.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.updavid.liveoci_hilt.core.database.convert.Converters
import com.updavid.liveoci_hilt.core.database.dao.LeisureActivityDao
import com.updavid.liveoci_hilt.core.database.dao.ScheduleDao
import com.updavid.liveoci_hilt.core.database.dao.UserDao
import com.updavid.liveoci_hilt.core.database.entities.ActivityEntity
import com.updavid.liveoci_hilt.core.database.entities.LeisureRecordEntity
import com.updavid.liveoci_hilt.core.database.entities.ScheduleEntity
import com.updavid.liveoci_hilt.core.database.entities.UserEntity

@Database(
    entities = [
        ScheduleEntity::class,
        UserEntity::class,
        ActivityEntity::class,
        LeisureRecordEntity::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase(){
    abstract fun scheduleDao(): ScheduleDao
    abstract fun userDao(): UserDao
    abstract fun leisureActivityDao(): LeisureActivityDao
}