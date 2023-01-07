package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class Review(
    val id: String,
    val rating: Int,
    val reviewText: String = "",
    val isAnonymous: Boolean,
    val createDateTime: String,
    val author: UserShort? = null
)