package com.example.movies_catalog.Network.Auth

@kotlinx.serialization.Serializable
data class RegisterRequestBody(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)