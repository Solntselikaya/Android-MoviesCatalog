package com.example.movies_catalog.network.auth

import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.LoginCredentials
import com.example.movies_catalog.network.models.TokenResponse
import com.example.movies_catalog.network.models.UserRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository {

    private val api: AuthApi = Network.getAuthApi()

    fun register(body: UserRegister): Flow<TokenResponse> = flow {
        val tokenData = api.register(body)
        Network.token = tokenData
        emit(tokenData)
    }.flowOn(Dispatchers.IO)

    //где один раз надо вернуть - тоже не нужен флоу
    //а вот где надо подписываться на изменения в бд - надо флоу!!
    fun login(body: LoginCredentials): Flow<TokenResponse> = flow {
        val tokenData = api.login(body)
        Network.token = tokenData
        emit(tokenData)
    }.flowOn(Dispatchers.IO)

    //suspend функция! ничего же не возвращается
    fun logout(): Flow<TokenResponse> = flow {
        val tokenData = api.logout()
        Network.token = tokenData
        emit(tokenData)
    }.flowOn(Dispatchers.IO)
}