package com.example.movies_catalog.network.movies

import com.example.movies_catalog.network.models.MovieDetails
import com.example.movies_catalog.network.models.MoviesPagedList
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("api/movies/{page}")
    suspend fun getMovies(@Path("page") page: Int): MoviesPagedList

    @GET("api/movies/details/{id}")
    suspend fun getMovieDetails(@Path("id") id: String): MovieDetails

}