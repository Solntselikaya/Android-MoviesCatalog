package com.example.movies_catalog.Network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @POST("api/favorites/{id}/add")
    suspend fun postFavorites(@Path("id") id: String): TokenResponse
}