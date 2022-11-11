package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class MovieDetails (
    val id: String,
    val name: String = "",
    val poster: String,
    val year: Int,
    val country: String = "",
    val genres: List<Genre> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val time: Int,
    val tagline: String = "",
    val description: String = "",
    val director: String = "",
    val budget: Int = 0,
    val fees: Int = 0,
    val ageLimit: Int
)