package com.example.pricetracker.data.network

import com.example.pricetracker.data.models.*
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class AuthDataSource @Inject constructor(private val userServices: RetrofitService) {
    suspend fun login(loginRequestModel: LoginRequestModel): BearerTokenModel? {
        userServices.login(loginRequestModel).let{
            if(!it.isSuccessful) throw HttpException(it)
            return it.body()
        }
    }

    suspend fun logout(): Response<Any> {
        userServices.logout().let{
            if(!it.isSuccessful) throw HttpException(it)
            return it
        }
    }

    suspend fun register(signUpModel: SignUpModel): SignedUpModel? {
        userServices.register(signUpModel).let{
            if(!it.isSuccessful) throw HttpException(it)
            return it.body()
        }
    }

    suspend fun forgotPasswordSend(emailModel: EmailModel): Response<Any> {
        userServices.forgotPasswordSend(emailModel).let{
            if(!it.isSuccessful) throw HttpException(it)
            return it
        }
    }

    suspend fun resetPassword(forgotPasswordModel: ForgotPasswordModel): Response<Any> {
        userServices.resetPassword(forgotPasswordModel).let{
            if(!it.isSuccessful) throw HttpException(it)
            return it
        }
    }
}