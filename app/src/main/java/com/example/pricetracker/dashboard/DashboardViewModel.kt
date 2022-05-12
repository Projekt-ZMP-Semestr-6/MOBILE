package com.example.pricetracker.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricetracker.data.network.AuthDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authDataSource: AuthDataSource
    ) : ViewModel() {

    init{
        
    }

    fun getCountries(){

        viewModelScope.launch {

        }
    }

}