package com.example.pricetracker.dashboard

import android.text.InputType
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricetracker.data.datastore.DataStoreRepository
import com.example.pricetracker.data.models.NameModel
import com.example.pricetracker.data.models.UpdateEmailModel
import com.example.pricetracker.data.models.UpdatePasswordModel
import com.example.pricetracker.data.models.UserModel
import com.example.pricetracker.data.network.AppDataSource
import com.example.pricetracker.data.network.AuthDataSource
import com.example.pricetracker.data.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val appDataSource: AppDataSource,
    private val dataStore: DataStoreRepository,
) : ViewModel() {
    var profileEditable: MutableLiveData<Int> = MutableLiveData(InputType.TYPE_NULL)
    var editOrSaveProfile: MutableLiveData<String> = MutableLiveData("Edit")
    var user: MutableLiveData<UserModel> = MutableLiveData()
    var userLoggedIn: MutableLiveData<Boolean> = MutableLiveData(true)
    var closeDialog: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        viewModelScope.launch {
            dataStore.getAccessToken().collectLatest {
                if(it.isEmpty()) userLoggedIn.postValue(false)
            }
        }
    }


    private val _uiState = MutableLiveData<UIState>(UIState.OnWaiting)
    val uiState: LiveData<UIState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.i("MyTag", "exceptionHandler exception = $exception")
        if (exception is HttpException) {
            _uiState.postValue(UIState.OnHttpFailure(exception.code()))
        } else {
            _uiState.postValue(UIState.OnFailure)
        }
        viewModelScope.launch {
            Log.i("MyTag", "exceptionHandler dataStore.saveAccessToken(\"\")")
            dataStore.saveAccessToken("")
        }
    }

    fun getCountries() {
        viewModelScope.launch {}
    }

    fun getUserData() {
        viewModelScope.launch(exceptionHandler) {
            appDataSource.getUser()?.let{
                user.postValue(it)
            }
        }
    }

    fun updateUserName(name: String) {
        _uiState.postValue(UIState.InProgress)
        viewModelScope.launch(exceptionHandler) {
            if (!user.value?.name.equals(name, true)){
                appDataSource.updateName(NameModel(name)).let{
                    if(it.isSuccessful){
                        _uiState.postValue(UIState.OnSuccess)
                        getUserData() // refresh data in view
                    }else{
                        _uiState.postValue(UIState.OnFailure)
                    }
                }
            }
        }
    }

    fun updateUserEmailAddress(email: String, password: String) {
        _uiState.postValue(UIState.InProgress)
        viewModelScope.launch(exceptionHandler) {
            appDataSource.updateEmail(UpdateEmailModel(email, password)).let{
                if(it.isSuccessful){
                    _uiState.postValue(UIState.OnSuccess)
                    getUserData() // refresh data in view
                    closeDialog.value = true
                }else{
                    _uiState.postValue(UIState.OnFailure)
                }
            }
        }
    }

    fun updateUserPassword(oldPassword: String, newPassword: String) {
        _uiState.postValue(UIState.InProgress)
        viewModelScope.launch(exceptionHandler) {
            appDataSource.updatePassword(UpdatePasswordModel(oldPassword, newPassword, newPassword)).let{
                if(it.isSuccessful){
                    _uiState.postValue(UIState.OnSuccess)
                    closeDialog.value = true
                }else{
                    _uiState.postValue(UIState.OnFailure)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(exceptionHandler) {
            Log.i("MyTag", "fun logout() {")
            _uiState.postValue(UIState.InProgress)
            authDataSource.logout().let{
                if(it.isSuccessful){
                    Log.i("MyTag", "fun logout()   if(it.isSuccessful)")
                    _uiState.postValue(UIState.OnSuccess)
                    dataStore.saveAccessToken("")
                }
                else{
                    Log.i("MyTag", " init   else{")
                    _uiState.postValue(UIState.OnFailure)
                }
            }
            Log.i("MyTag", " init   else222")
        }
    }
}