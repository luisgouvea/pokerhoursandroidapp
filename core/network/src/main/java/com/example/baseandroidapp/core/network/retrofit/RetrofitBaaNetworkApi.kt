package com.example.baseandroidapp.core.network.retrofit

import com.example.baseandroidapp.core.network.model.UserResponse
import kotlinx.serialization.Serializable
import retrofit2.http.GET


/**
 * Retrofit API declaration for BAA Network API
 */
interface RetrofitBaaNetworkApi {
    @GET("/users")
    suspend fun getUsers(): List<UserResponse>
}

const val BaaBaseUrl = "url"

/**
 * Wrapper for data provided from the [BaaBaseUrl]
 */
@Serializable
data class NetworkResponse<T>(
    val data: T,
)