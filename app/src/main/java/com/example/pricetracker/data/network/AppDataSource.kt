package com.example.pricetracker.data.network

import com.example.pricetracker.data.models.*
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class AppDataSource @Inject constructor(private val userServices: RetrofitService) {
    suspend fun getUser(): UserModel? {
        userServices.getUser().let {
            if (!it.isSuccessful) throw HttpException(it)
            return it.body()
        }
    }

    suspend fun updatePassword(updatePasswordModel: UpdatePasswordModel): Response<Any> {
        userServices.updatePassword(updatePasswordModel).let {
            if (!it.isSuccessful) throw HttpException(it)
            return it
        }
    }

    suspend fun updateName(nameModel: NameModel): Response<Any> {
        userServices.updateName(nameModel).let {
            if (!it.isSuccessful) throw HttpException(it)
            return it
        }
    }

    suspend fun updateEmail(emailModel: UpdateEmailModel): Response<Any> {
        userServices.updateEmail(emailModel).let {
            if (!it.isSuccessful) throw HttpException(it)
            return it
        }
    }

    suspend fun deleteAccount(passwordModel: PasswordModel): Response<Any> {
        return userServices.deleteAccount(passwordModel)
    }
}