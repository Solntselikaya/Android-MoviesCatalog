package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class MoviesList(
    val movies: List<MovieElement> = emptyList()
)