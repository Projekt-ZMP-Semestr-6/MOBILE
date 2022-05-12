package com.example.pricetracker.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BearerTokenModel(
    @SerializedName("Bearer") var bearerToken: String?,
) : Parcelable