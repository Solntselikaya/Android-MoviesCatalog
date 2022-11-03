package com.example.movies_catalog.network.favoriteMovies

@kotlinx.serialization.Serializable
data class Genre (
    val id: String,
    val name: String
)