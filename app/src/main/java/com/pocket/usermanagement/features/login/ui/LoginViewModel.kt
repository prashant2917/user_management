package com.pocket.usermanagement.features.login.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.datastore.data.domain.DataStoreUseCase
import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.domain.LoginUseCase
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {

    private val _mutableStateFlowLoginSuccess = MutableStateFlow<LoginEntity?>(null)
    var mutableStateFlowLoginSuccess = _mutableStateFlowLoginSuccess

    private val _mutableStateFlowLoginException = MutableStateFlow<Exception?>(null)
    val mutableStateFlowLoginException = _mutableStateFlowLoginException.asStateFlow()

    private val _mutableStateFlowLoading = MutableStateFlow(false)
    val mutableStateFlowLoading = _mutableStateFlowLoading

    fun resetValues() {
        _mutableStateFlowLoginException.value = null
        _mutableStateFlowLoginSuccess.value = null
        _mutableStateFlowLoading.value = false
    }

    fun doLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            resetValues()
            loginUseCase.userLogin(loginRequest).collect { resultEvent ->
                when (resultEvent) {
                    is ResultEvent.Loading -> {
                        AppLogger.d("Loading")
                        _mutableStateFlowLoading.value = true
                    }

                    is ResultEvent.Error -> {
                        AppLogger.d("Error ${resultEvent.exception.message}")
                        _mutableStateFlowLoading.value = false
                        _mutableStateFlowLoginException.value = resultEvent.exception

                    }

                    is ResultEvent.Success -> {
                        AppLogger.d("Success ${resultEvent.data}")
                        _mutableStateFlowLoading.value = false
                        _mutableStateFlowLoginSuccess.value = resultEvent.data
                    }
                }
            }
        }
    }

    fun setIsUserLogin(isUserLogin: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCase.setIsUserLogin(isUserLogin)
        }
    }

    fun saveUserId(userId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCase.saveUserId(userId)
        }
    }
}