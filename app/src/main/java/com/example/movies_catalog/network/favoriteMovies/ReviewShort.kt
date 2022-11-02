package com.example.movies_catalog.network.favoriteMovies

@kotlinx.serialization.Serializable
data class ReviewShort (
    val id: String,
    val rating: Int
)