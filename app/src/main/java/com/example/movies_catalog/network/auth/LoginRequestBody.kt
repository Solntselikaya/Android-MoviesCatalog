package com.example.movies_catalog.network.auth

@kotlinx.serialization.Serializable
data class LoginRequestBody (
    val username: String,
    val password: String
)