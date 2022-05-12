package com.example.pricetracker.data.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveAccessToken(data: String)
    fun getAccessToken(): Flow<String>
    suspend fun saveIsFirstLaunch(data: Boolean)
    suspend fun getIsFirstLaunch(): Flow<Boolean>
}