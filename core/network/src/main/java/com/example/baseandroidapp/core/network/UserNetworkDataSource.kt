package com.example.baseandroidapp.core.network

import com.example.baseandroidapp.core.network.model.UserResponse

interface UserNetworkDataSource {
    suspend fun fetchUser(): List<UserResponse>
    //suspend fun fetchUserById(id: String): UserResponse
}