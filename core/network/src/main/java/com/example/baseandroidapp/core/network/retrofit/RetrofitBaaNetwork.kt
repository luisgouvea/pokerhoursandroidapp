package com.example.baseandroidapp.core.network.retrofit

import com.example.network.BuildConfig
import com.example.baseandroidapp.core.network.BaaNetworkDataSource
import com.example.baseandroidapp.core.network.model.UserResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [Retrofit] backed [BaaNetworkDataSource]
 */
@Singleton
class RetrofitBaaNetwork @Inject constructor(
    okHttpClient: OkHttpClient
) : BaaNetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            MoshiConverterFactory.create(
                getMoshi()
            )
        )
        .build()
        .create(RetrofitBaaNetworkApi::class.java)

    private fun getMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    override suspend fun fetchUser(): List<UserResponse> =
        networkApi.getUsers()

}