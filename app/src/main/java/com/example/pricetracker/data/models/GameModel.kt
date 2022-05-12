package com.example.pricetracker.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameModel(
    val id: Int,
    val name: String,
    val appid: Int,
    val last_modified: Long,
    val header_img: String,
) : Parcelable