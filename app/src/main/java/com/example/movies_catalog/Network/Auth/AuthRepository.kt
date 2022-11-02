package com.example.movies_catalog.Network.Auth

import com.example.movies_catalog.Network.Network
import com.example.movies_catalog.Network.TokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository {

    private val api: AuthApi = Network.getAuthApi()

    fun register(body: RegisterRequestBody): Flow<TokenResponse> = flow{
        val tokenData = api.register(body)
        Network.token = tokenData
        emit(tokenData)
    }.flowOn(Dispatchers.IO)
}