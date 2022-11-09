package com.example.movies_catalog.network.auth

import com.example.movies_catalog.network.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/account/register")
    suspend fun register(@Body body: UserRegister): TokenResponse

    @POST("api/account/login")
    suspend fun login(@Body body: LoginCredentials): TokenResponse

    @POST("api/account/logout")
    suspend fun logout(): TokenResponse
}