package com.example.movies_catalog.network.auth

@kotlinx.serialization.Serializable
data class UserRegister(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)