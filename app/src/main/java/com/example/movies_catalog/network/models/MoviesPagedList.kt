package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class MoviesPagedList (
    val movies: List<MovieElement>,
    val pageInfo: PageInfo
)