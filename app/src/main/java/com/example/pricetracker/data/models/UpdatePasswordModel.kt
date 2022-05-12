package com.example.pricetracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdatePasswordModel(
    val old_password: String,
    val new_password: String,
    val new_password_confirmation: String,
) : Parcelable