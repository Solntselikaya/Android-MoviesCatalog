package com.example.movies_catalog.network.favoriteMovies

import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.TokenResponse
import com.example.movies_catalog.network.models.MoviesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavoriteMoviesRepository {
    private val api: FavoriteMoviesApi = Network.getFavoriteMoviesApi()

    fun getFavorites(): Flow<MoviesList> = flow {
        val moviesData = api.getFavorites()
        Network.favoriteMovies = moviesData
        emit(moviesData)
    }.flowOn(Dispatchers.IO)

    fun addFavorites(body: String): Flow<TokenResponse> = flow {
        val tokenData = api.addFavorites(body)
        Network.token = tokenData
        emit(tokenData)
    }.flowOn(Dispatchers.IO)

    fun deleteFavorites(body: String): Flow<TokenResponse> = flow {
        val tokenData = api.deleteFavorites(body)
        Network.token = tokenData
        emit(tokenData)
    }.flowOn(Dispatchers.IO)
}