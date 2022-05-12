package com.example.pricetracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpModel(
    val email: String,
    val name: String,
    val password: String,
    val password_confirmation: String,
) : Parcelable