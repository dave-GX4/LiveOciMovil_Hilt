package com.updavid.liveoci_hilt.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.updavid.liveoci_hilt.core.database.entities.ActivityEntity
import com.updavid.liveoci_hilt.core.database.entities.LeisureRecordEntity

data class LeisureWithActivityRelation(
    @Embedded val leisure: LeisureRecordEntity,

    @Relation(
        parentColumn = "uuidActivity",
        entityColumn = "uuid"
    )
    val activity: ActivityEntity
)
