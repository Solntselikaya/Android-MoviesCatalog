package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class UserProfile(
    val id: String,
    val nickName: String = "",
    val email: String,
    val avatarLink: String = "",
    val name: String,
    val birthDate: String,
    val gender: Int
)