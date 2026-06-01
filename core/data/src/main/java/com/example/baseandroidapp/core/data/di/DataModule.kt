package com.example.baseandroidapp.core.data.di

import com.example.baseandroidapp.core.data.repository.UserRepository
import com.example.baseandroidapp.core.data.repository.UserRepositoryImpl
import com.example.baseandroidapp.core.data.util.ConnectivityManagerNetworkMonitor
import com.example.baseandroidapp.core.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}