package com.example.movies_catalog.network.favoriteMovies

@kotlinx.serialization.Serializable
data class MoviesList (
    val movies: Array<MovieElement>
)