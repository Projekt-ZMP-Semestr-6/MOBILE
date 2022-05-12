package com.example.pricetracker.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.pricetracker.BuildConfig
import com.example.pricetracker.data.Constants.BASE_URL
import com.example.pricetracker.data.datastore.DataStorePreferences.prefsDataStore
import com.example.pricetracker.data.datastore.DataStoreRepository
import com.example.pricetracker.data.datastore.DataStoreRepositoryImpl
import com.example.pricetracker.data.network.AuthDataSource
import com.example.pricetracker.data.network.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext applicationContext: Context): DataStore<Preferences> =
        applicationContext.prefsDataStore

    @Provides
    @Singleton
    fun provideDataStoreRepository(prefsDataStore: DataStore<Preferences>): DataStoreRepository =
        DataStoreRepositoryImpl(prefsDataStore)

    @Provides
    @Singleton
    fun provideTokenInterceptor(dataStoreRepository: DataStoreRepositoryImpl): TokenInterceptor =
        TokenInterceptor(dataStoreRepository)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttp(dataStoreRepository: DataStoreRepositoryImpl): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            client.addInterceptor(loggingInterceptor)
        }
        return client
            .addInterceptor(TokenInterceptor(dataStoreRepository))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttp: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)
        .client(okHttp)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providesAuthDataSource(userServices: RetrofitService) = AuthDataSource(userServices)

    @Provides
    @Singleton
    fun provideRetrofitService(serviceBuilder: ServiceBuilder): RetrofitService =
        serviceBuilder.buildService(RetrofitService::class.java)
}