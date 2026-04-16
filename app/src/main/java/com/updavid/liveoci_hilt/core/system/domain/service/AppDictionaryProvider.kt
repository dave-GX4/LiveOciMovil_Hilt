package com.updavid.liveoci_hilt.core.system.domain.service

import com.updavid.liveoci_hilt.core.system.domain.entity.DictionaryAppInfo

interface AppDictionaryProvider {
    suspend fun getDictionary(): Result<Map<String, DictionaryAppInfo>>
}