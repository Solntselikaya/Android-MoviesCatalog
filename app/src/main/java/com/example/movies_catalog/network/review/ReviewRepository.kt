package com.example.movies_catalog.network.review

import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.ReviewModify

class ReviewRepository {

    private val api: ReviewApi = Network.getReviewApi()

    suspend fun postReview(movieId: String, body: ReviewModify) {
        api.postReview(movieId, body)
    }

    suspend fun putReview(movieId: String, id: String, body: ReviewModify) {
        api.putReview(movieId, id, body)
    }

    suspend fun deleteReview(movieId: String, id: String) {
        api.deleteReview(movieId, id)
    }

}