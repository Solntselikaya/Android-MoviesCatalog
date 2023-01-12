package com.example.movies_catalog.network.review

import com.example.movies_catalog.network.models.ReviewModify
import retrofit2.http.*

interface ReviewApi {

    @POST("api/movie/{movieId}/review/add")
    suspend fun postReview(@Path("movieId") movieId: String, @Body body: ReviewModify)

    @PUT("api/movie/{movieId}/review/{id}/edit")
    suspend fun putReview(@Path("movieId") movieId: String, @Path("id") id: String, @Body body: ReviewModify)

    @DELETE("api/movie/{movieId}/review/{id}/delete")
    suspend fun deleteReview(@Path("movieId") movieId: String, @Path("id") id: String)

}