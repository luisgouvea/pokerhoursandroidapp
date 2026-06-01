package com.example.baseandroidapp.core.network

import com.example.baseandroidapp.core.network.model.UserResponse


/**
 * Interface representing network calls to the BAA backend
 */
interface BaaNetworkDataSource {
    suspend fun fetchUser(): List<UserResponse>
}