package com.example.movies_catalog.Network

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {

    @POST("api/account/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenResponse
}