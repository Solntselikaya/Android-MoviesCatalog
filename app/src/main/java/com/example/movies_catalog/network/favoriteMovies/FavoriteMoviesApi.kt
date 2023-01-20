package com.example.movies_catalog.network.favoriteMovies

import com.example.movies_catalog.network.models.MoviesList
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @GET("api/favorites")
    suspend fun getFavorites(): MoviesList

    @POST("api/favorites/{id}/add")
    suspend fun addFavorites(@Path("id") id: String)

    @DELETE("api/favorites/{id}/delete")
    suspend fun deleteFavorites(@Path("id") id: String)
}