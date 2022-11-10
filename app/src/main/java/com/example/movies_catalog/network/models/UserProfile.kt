package com.example.movies_catalog.network.models

data class UserProfile (
    val id: String,
    val nickName: String,
    val email: String,
    val avatarLink: String? = null,
    val name: String,
    val birthDate: String,
    val gender: Int
)