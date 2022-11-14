package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class Genre (
    val id: String,
    val name: String = ""
)