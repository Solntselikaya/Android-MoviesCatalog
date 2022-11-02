package com.example.movies_catalog.Network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request().newBuilder().apply {
            Network.token?.let {
                addHeader("accept", "application/json")
                addHeader("content-Type", "application/x-www-form-urlencoded")
                addHeader("Access-Token", "Bearer: ${it.token}")
            }
        }.build()

        var response: Response? = null

        return try{
            response = chain.proceed(request)
            response
        } catch (e: Exception){
            response?.close()
            chain.proceed(request)
        }
    }
}