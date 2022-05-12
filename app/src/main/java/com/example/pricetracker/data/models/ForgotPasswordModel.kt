package com.example.pricetracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForgotPasswordModel(
    val token: String,
    val email: String,
    val password: String,
    val password_confirmation: String,
) : Parcelable