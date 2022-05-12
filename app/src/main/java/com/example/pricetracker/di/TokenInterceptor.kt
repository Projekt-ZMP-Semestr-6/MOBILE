package com.example.pricetracker.di

import android.util.Log
import com.example.pricetracker.data.datastore.DataStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val dataStore: DataStoreRepository) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        val token = runBlocking { dataStore.getAccessToken().first() }
        Log.i("MyTag", "TokenInterceptor URL = ${request.url}")
        if (request.url.toString().contains("/api/auth", true) ||
            request.url.toString().contains("/api/forgot-password", true)
        ) {
            Log.i("MyTag", "Request without Token")
            requestBuilder.header("Authorization", "Bearer $token")
        } else {
            Log.i("MyTag", "Request with Token $token")
            requestBuilder.header("Authorization", "Bearer $token")
        }

        val originalRequest = requestBuilder.build()
        return chain.proceed(originalRequest)
    }
}