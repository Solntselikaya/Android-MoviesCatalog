package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class ReviewModify (
    val reviewText: String,
    val rating: Int,
    val isAnonymous: Boolean
)