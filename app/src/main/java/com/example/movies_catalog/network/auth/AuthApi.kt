package com.example.movies_catalog.network.auth

import com.example.movies_catalog.network.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/account/register")
    suspend fun register(@Body body: RegisterRequestBody): TokenResponse

    @POST("api/account/login")
    suspend fun login(@Body body: LoginRequestBody): TokenResponse

    @POST("api/account/logout")
    suspend fun logout(): TokenResponse
}