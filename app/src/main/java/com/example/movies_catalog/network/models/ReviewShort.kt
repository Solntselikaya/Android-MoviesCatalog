package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class ReviewShort (
    val id: String,
    val rating: Int
)