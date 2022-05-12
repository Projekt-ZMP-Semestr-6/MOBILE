package com.example.pricetracker.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar

fun Activity.snackBarExtension(message: String, period: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, window.decorView.rootView, message, period).show()
}