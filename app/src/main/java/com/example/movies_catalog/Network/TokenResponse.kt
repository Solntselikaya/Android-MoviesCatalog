package com.example.movies_catalog.Network

@kotlinx.serialization.Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)