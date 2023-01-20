package com.example.movies_catalog.network

import com.example.movies_catalog.network.auth.AuthApi
import com.example.movies_catalog.network.favoriteMovies.FavoriteMoviesApi
import com.example.movies_catalog.network.models.*
import com.example.movies_catalog.network.movies.MoviesApi
import com.example.movies_catalog.network.review.ReviewApi
import com.example.movies_catalog.network.user.UserApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Network {

    private const val BASE_URL = "https://react-midterm.kreosoft.space/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private fun getHttpClient(): OkHttpClient {

        val client = OkHttpClient.Builder().apply {
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            addInterceptor(Interceptor())
            // Authenticator
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }

        return client.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getRetrofit(): Retrofit {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(getHttpClient())
            .build()

        return retrofit
    }

    private val retrofit: Retrofit = getRetrofit()

    //в нетворке этого быть не должно..
    var token: TokenResponse? = null
    var favoriteMovies: MoviesList? = null
    var movies: MoviesPagedList? = null
    var profile: UserProfile? = null
    var movieDetails: MovieDetails? = null

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)
    fun getFavoriteMoviesApi(): FavoriteMoviesApi = retrofit.create(FavoriteMoviesApi::class.java)
    fun getMoviesApi(): MoviesApi = retrofit.create(MoviesApi::class.java)
    fun getUserApi(): UserApi = retrofit.create(UserApi::class.java)
    fun getReviewApi(): ReviewApi = retrofit.create(ReviewApi::class.java)

    fun clearData() {
        token = null
        favoriteMovies = null
        movies = null
        profile = null
        movieDetails = null
    }
}