package com.example.movies_catalog.network.movie

import com.example.movies_catalog.network.Network
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
}