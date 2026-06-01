package com.example.baseandroidapp.core.network.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun providesOkhttpClient(
    ): OkHttpClient {
        return createOkHttpClient()
    }

    fun createOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor { chain -> createParametersDefault(chain) }
            .addInterceptor(httpLoggingInterceptor).build()

    }

    fun createParametersDefault(chain: Interceptor.Chain): Response {
        val timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        var request = chain.request()
        val builder = request.url.newBuilder()

        builder.addQueryParameter("apikey", com.example.network.BuildConfig.API_PUBLIC)
            .addQueryParameter(
                "hash",
                HashGenerate.generate(timeStamp, com.example.network.BuildConfig.API_PRIVATE, com.example.network.BuildConfig.API_PUBLIC)
            )
            .addQueryParameter("ts", timeStamp.toString())

        request = request.newBuilder().url(builder.build()).build()
        return chain.proceed(request)
    }

    object HashGenerate {

        fun generate(timestamp: Long, privateKey: String, publicKey: String): String {
            return (timestamp.toString() + privateKey + publicKey).toMD5()
        }

        private fun String.toMD5(): String {
            val md = MessageDigest.getInstance("MD5")
            val digested = md.digest(toByteArray())
            return digested.joinToString("") { String.format("%02x", it) }
        }
    }


}