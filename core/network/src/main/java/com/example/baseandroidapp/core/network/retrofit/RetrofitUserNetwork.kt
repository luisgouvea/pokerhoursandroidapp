package com.example.baseandroidapp.core.network.retrofit

import com.example.baseandroidapp.core.network.UserNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUserNetwork @Inject constructor(
    private val api: RetrofitBaaNetwork
) : UserNetworkDataSource {
    override suspend fun fetchUser() = api.networkApi.getUsers()
}