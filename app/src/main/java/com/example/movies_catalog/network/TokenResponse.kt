package com.example.movies_catalog.network

@kotlinx.serialization.Serializable
data class TokenResponse(
    val token: String
)