package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class LoginCredentials(
    val username: String,
    val password: String
)