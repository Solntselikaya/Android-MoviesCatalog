package com.example.movies_catalog.network.favoriteMovies

import com.example.movies_catalog.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoriteMoviesRepository {
    private val api: FavoriteMoviesApi = Network.getFavoriteMoviesApi()

    fun getFavorites(): Flow<MoviesList> = flow{
        val moviesData = api.getFavorites()
        Network.favoriteMovies = moviesData
        emit(moviesData)
    }.flowOn(Dispatchers.IO)
}