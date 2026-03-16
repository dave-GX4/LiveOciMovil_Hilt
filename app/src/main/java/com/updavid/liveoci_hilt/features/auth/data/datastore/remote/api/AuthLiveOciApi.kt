package com.updavid.liveoci_hilt.features.auth.data.datastore.remote.api

import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.request.AuthSingIn
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.request.AuthSingUp
import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.response.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthLiveOciApi {
    @POST("")
    suspend fun postSingIn(
        @Body request: AuthSingIn
    ): AuthResponseDto

    @POST("")
    suspend fun postSingUp(
        @Body request: AuthSingUp
    ): AuthResponseDto
}