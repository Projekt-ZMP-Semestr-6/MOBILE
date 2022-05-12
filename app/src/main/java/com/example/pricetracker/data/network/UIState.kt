package com.example.pricetracker.data.network

sealed class UIState {
    object InProgress : UIState()
    object OnSuccess : UIState()
    object OnWaiting : UIState()
    object OnFailure : UIState()
    data class OnHttpFailure(val errorCode: Int) : UIState()
}