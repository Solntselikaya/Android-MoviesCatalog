package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class UserShort (
    val userId: String,
    val nickName: String = "",
    val avatar: String = ""
)