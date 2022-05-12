package com.example.pricetracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignedUpModel(
    val id: String,
    val name: String,
    val email: String,
) : Parcelable