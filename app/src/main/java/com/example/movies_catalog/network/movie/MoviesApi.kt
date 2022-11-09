package com.example.movies_catalog.network.movie

import com.example.movies_catalog.network.models.MoviesDetails
import com.example.movies_catalog.network.models.MoviesPagedList
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("api/movies/{page}")
    suspend fun getMovies(@Path("page") page: Int): MoviesPagedList

    @GET("api/movies/details/{id}")
    suspend fun getMoviesDetails(@Path("id") id: String): MoviesDetails

}