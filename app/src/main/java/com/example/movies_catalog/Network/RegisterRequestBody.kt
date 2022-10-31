package com.example.movies_catalog.Network

import java.util.*

@kotlinx.serialization.Serializable
data class RegisterRequestBody(
    val userName: String,
    val name: String,
    //val password: String,
    //val email: String,
    //val birthDate: Date,
    //val gender: Int
)