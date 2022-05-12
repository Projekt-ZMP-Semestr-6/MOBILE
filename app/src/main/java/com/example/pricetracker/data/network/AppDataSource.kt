package com.example.pricetracker.data.network

import com.example.pricetracker.data.models.*
import retrofit2.Response
import javax.inject.Inject

class AppDataSource @Inject constructor(private val userServices: RetrofitService) {
    suspend fun getUser(): UserModel? {
        return userServices.getUser().body()
    }

    suspend fun updatePassword(updatePasswordModel: UpdatePasswordModel): Response<Any> {
        return userServices.updatePassword(updatePasswordModel)
    }

    suspend fun updateName(nameModel: NameModel): Response<Any> {
        return userServices.updateName(nameModel)
    }

    suspend fun updateEmail(emailModel: UpdateEmailModel): Response<Any> {
        return userServices.updateEmail(emailModel)
    }

    suspend fun deleteAccount(passwordModel: PasswordModel): Response<Any> {
        return userServices.deleteAccount(passwordModel)
    }
}