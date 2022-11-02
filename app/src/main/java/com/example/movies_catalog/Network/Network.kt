package com.example.movies_catalog.Network

import com.example.movies_catalog.Network.Auth.AuthApi
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

    var token: TokenResponse? = null

    fun getAuthApi(): AuthApi = retrofit.create(AuthApi::class.java)
}