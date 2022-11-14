package com.example.movies_catalog.network.auth

@kotlinx.serialization.Serializable
data class LoginCredentials (
    val username: String,
    val password: String
)