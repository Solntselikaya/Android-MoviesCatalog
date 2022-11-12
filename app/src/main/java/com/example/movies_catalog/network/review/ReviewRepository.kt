package com.example.movies_catalog.network.review

import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.ReviewModify
import com.example.movies_catalog.network.models.UserProfile

class ReviewRepository {

    private val api: ReviewApi = Network.getReviewApi()

    suspend fun postReview(movieId: String, body: ReviewModify){
        api.postReview(movieId, body)
    }

    suspend fun putReview(movieId: String, id: String){
        api.putReview(movieId, id)
    }

    suspend fun deleteReview(movieId: String, id: String){
        api.deleteReview(movieId, id)
    }

}