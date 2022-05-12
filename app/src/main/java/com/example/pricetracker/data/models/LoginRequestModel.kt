package com.example.pricetracker.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginRequestModel(
    var email: String = "",
    var password: String? = null,
    @SerializedName("device_name") var deviceName: String = "desktop",
): Parcelable