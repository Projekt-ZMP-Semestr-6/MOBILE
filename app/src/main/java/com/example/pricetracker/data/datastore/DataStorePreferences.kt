package com.example.pricetracker.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object DataStorePreferences {
    private const val PREFERENCES_NAME = "DATA_STORE_PRICE_TRACKER"
    val Context.prefsDataStore by preferencesDataStore(
        name = PREFERENCES_NAME
    )

    val ACCESS_TOKEN = stringPreferencesKey("com.example.pricetracker.ACCESS_TOKEN")
    val FIRST_LAUNCH = booleanPreferencesKey("com.example.pricetracker.FIRST_LAUNCH")
}