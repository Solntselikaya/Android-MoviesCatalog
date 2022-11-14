package com.example.movies_catalog.network.user

import com.example.movies_catalog.network.Network
import com.example.movies_catalog.network.models.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    private val api: UserApi = Network.getUserApi()

    fun getUserData(): Flow<UserProfile> = flow{
        val userProfile = api.getProfile()
        Network.profile = userProfile
        emit(userProfile)
    }.flowOn(Dispatchers.IO)

    suspend fun putUserData(body: UserProfile){
        api.updateProfile(body)
    }
}