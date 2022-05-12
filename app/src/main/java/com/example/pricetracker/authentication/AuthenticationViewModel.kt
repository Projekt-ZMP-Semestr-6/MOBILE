package com.example.pricetracker.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricetracker.data.datastore.DataStoreRepository
import com.example.pricetracker.data.models.EmailModel
import com.example.pricetracker.data.models.LoginRequestModel
import com.example.pricetracker.data.models.SignUpModel
import com.example.pricetracker.data.network.AuthDataSource
import com.example.pricetracker.data.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    private val _uiState = MutableLiveData<UIState>(UIState.OnWaiting)
    val uiState: LiveData<UIState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.i("MyTag", "Exception -> $exception")
        if (exception is HttpException) {
            Log.i("MyTag", "Exception reason -> ${exception.code()}")
            _errorMessage.postValue(
                when (exception.code()) {
                    403 -> "Forbidden"
                    422 -> "Unprocessable content"
                    429 -> "Too many requests"
                    else -> ""
                }
            )
            _uiState.postValue(UIState.OnHttpFailure(exception.code()))
        }else{
            _uiState.postValue(UIState.OnFailure)
        }
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            dataStore.getAccessToken().let {
                it.collectLatest { token ->
                    if (token.isNotBlank()) {
                        // Redirect user to Dashboard instant
//                        _uiState.postValue(UIState.OnSuccess)
                    }
                }
            }
        }
    }

    fun clearUIState() {
        _uiState.value = UIState.OnWaiting
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.postValue(UIState.InProgress)
            authDataSource.login(
                LoginRequestModel(
                    email = email,
                    password = password
                )
            )?.let {
                it.bearerToken?.let { it1 ->
                    dataStore.saveAccessToken(it1)
                }
                Log.i("MyTag", "Token = ${it.bearerToken}")
                _uiState.postValue(UIState.OnSuccess)
            }
        }
    }

    fun register(email: String, name: String, password: String, password_confirmation: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.postValue(UIState.InProgress)
            authDataSource.register(
                SignUpModel(
                    email = email,
                    name = name,
                    password = password,
                    password_confirmation = password_confirmation
                )
            )?.let {
                Log.i("MyTag", "SignedUpModel = $it")
                _uiState.postValue(UIState.OnSuccess)
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.postValue(UIState.InProgress)
            authDataSource.forgotPasswordSend(
                EmailModel(
                    email = email
                )
            ).let {
                Log.i("MyTag", "Reset password = ${it.body()}")
                _uiState.postValue(UIState.OnSuccess)
            }
        }
    }
}