package com.example.pricetracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateEmailModel(
    val email: String,
    val password: String,
) : Parcelable