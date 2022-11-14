package com.example.movies_catalog.network.models

@kotlinx.serialization.Serializable
data class PageInfo(
    val pageSize: Int,
    val pageCount: Int,
    val currentPage: Int
)