package com.example.movies_catalog.network.movies

import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.MovieDetails
import com.example.movies_catalog.network.models.MoviesPagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoviesRepository {

    private val api: MoviesApi = Network.getMoviesApi()

    fun getMovies(body: Int): Flow<MoviesPagedList> = flow{
        val moviesData = api.getMovies(body)
        Network.movies = moviesData
        emit(moviesData)
    }.flowOn(Dispatchers.IO)

    fun getMovieDetails(body: String): Flow<MovieDetails> = flow<MovieDetails> {
        val movieData = api.getMovieDetails(body)
        Network.movieDetails = movieData
        emit(movieData)
    }.flowOn(Dispatchers.IO)

}