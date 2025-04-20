package com.pocket.usermanagement.features.login.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.data.usecase.LoginUseCase
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase):ViewModel() {

    private val _mutableStateFlowLoginSuccess = MutableStateFlow<LoginEntity?>(null)
    var mutableStateFlowLoginSuccess = _mutableStateFlowLoginSuccess

    private val _mutableStateFlowLoginError = MutableStateFlow<String?>("")
    val mutableStateFlowLoginError = _mutableStateFlowLoginError

    private val _mutableStateFlowLoading = MutableStateFlow(false)
    val mutableStateFlowLoading = _mutableStateFlowLoading


    fun doLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.userLogin(loginRequest).collect { resultEvent ->
                when (resultEvent) {
                    is ResultEvent.Loading -> {
                        AppLogger.d("Loading")
                        _mutableStateFlowLoading.value = true
                    }

                    is ResultEvent.Error -> {
                        AppLogger.d("Error ${resultEvent.exception}")
                        _mutableStateFlowLoading.value = false
                        _mutableStateFlowLoginError.value = resultEvent.exception.toString()
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
}