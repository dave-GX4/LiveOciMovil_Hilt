package com.updavid.liveoci_hilt.core.system.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryAppInfo(
    val categoryName: String,
    val isLeisure: Boolean
)