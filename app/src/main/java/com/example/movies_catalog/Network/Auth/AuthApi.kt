package com.example.movies_catalog.Network.Auth

import com.example.movies_catalog.Network.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/account/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenResponse
}