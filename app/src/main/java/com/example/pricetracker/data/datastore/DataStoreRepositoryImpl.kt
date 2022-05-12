package com.example.pricetracker.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.pricetracker.data.datastore.DataStorePreferences.ACCESS_TOKEN
import com.example.pricetracker.data.datastore.DataStorePreferences.FIRST_LAUNCH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val prefsDataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun saveAccessToken(data: String) {
        prefsDataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = data
        }
    }

    override fun getAccessToken(): Flow<String> = prefsDataStore.data
        .catch { exception ->
            when (exception) {
                is IOException -> {
                    emit(emptyPreferences())
                }
                else -> throw exception
            }
        }.map { preferences ->
            preferences[ACCESS_TOKEN] ?: ""
        }

    override suspend fun saveIsFirstLaunch(data: Boolean) {
        prefsDataStore.edit { preferences ->
            preferences[FIRST_LAUNCH] = data
        }
    }

    override suspend fun getIsFirstLaunch(): Flow<Boolean> = prefsDataStore.data
        .catch { exception ->
            when (exception) {
                is IOException -> {
                    emit(emptyPreferences())
                }
                else -> throw exception
            }
        }.map { preferences ->
            preferences[FIRST_LAUNCH] ?: true
        }
}