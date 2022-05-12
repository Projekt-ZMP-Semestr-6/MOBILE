package com.example.pricetracker.di

import android.util.Log
import com.example.pricetracker.data.datastore.DataStoreRepository
import com.example.pricetracker.data.models.BearerTokenModel
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.wait
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val dataStore: DataStoreRepository) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
        val originalRequest = requestBuilder.build()
        var response = chain.proceed(originalRequest)

        Log.i("MyTag", "TokenInterceptor URL = ${request.url}")
        if (request.url.toString().contains("/api/auth", true) ||
            request.url.toString().contains("/api/forgot-password", true)
        ) {
            Log.i("MyTag", "Request without Token")
            requestBuilder.header("Authorization", "Bearer ${dataStore.getAccessToken()}")
        } else {
            Log.i("MyTag", "Request with Token")
            requestBuilder.header("Authorization", "Bearer ${dataStore.getAccessToken()}")
        }

        return response
    }
}