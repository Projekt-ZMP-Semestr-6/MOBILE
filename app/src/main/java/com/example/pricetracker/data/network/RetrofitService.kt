package com.example.pricetracker.data.network

import com.example.pricetracker.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
//  AUTHENTICATION
    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequestModel): Response<BearerTokenModel>

    @GET("/api/auth/logout")
    suspend fun logout(): Response<Any>

    @POST("/api/auth/register")
    suspend fun register(@Body signUpModel: SignUpModel): Response<SignedUpModel>

//  PASSWORD
    @POST("/api/forgot-password/send")
    suspend fun forgotPasswordSend(@Body email: EmailModel): Response<Any>

    @GET("/api/forgot-password/reset")
    suspend fun resetPassword(@Body forgotPasswordModel: ForgotPasswordModel): Response<Any>

//  CORE
    @GET("/api/user")
    suspend fun getUser(): Response<UserModel>

    @PUT("/api/user/password")
    suspend fun updatePassword(@Body updatePasswordModel: UpdatePasswordModel): Response<Any>

    @PUT("/api/user/name")
    suspend fun updateName(@Body updateNameModel: NameModel): Response<Any>

    @PUT("/api/user/email")
    suspend fun updateEmail(@Body updateEmailModel: UpdateEmailModel): Response<Any>

    @DELETE("/api/user/delete")
    suspend fun deleteAccount(@Body passwordModel: PasswordModel): Response<Any>

    @GET("/api/bestsellers")
    suspend fun getBestsellers(): Response<ArrayList<GameModel>>
} //    : Response<ArrayList<BestsellerModel>>?