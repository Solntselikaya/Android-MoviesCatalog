package com.example.movies_catalog.network.user

import com.example.movies_catalog.network.models.UserProfile
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {

    @GET("api/account/profile")
    suspend fun getProfile(): UserProfile

    @PUT("api/account/profile")
    suspend fun updateProfile(@Body body: UserProfile)

}