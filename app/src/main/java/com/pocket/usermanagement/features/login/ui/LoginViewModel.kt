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

    private val _mutableStateFlowLoginError = MutableStateFlow<String?>("")
    val mutableStateFlowLoginError = _mutableStateFlowLoginError.asStateFlow()

    private val _mutableStateFlowLoading = MutableStateFlow(false)
    val mutableStateFlowLoading = _mutableStateFlowLoading

    fun resetValues() {
        _mutableStateFlowLoginError.value = ""
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
                        _mutableStateFlowLoginError.value = resultEvent.exception.message
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

    fun saveUserDetails(loginEntity: LoginEntity?) {
        viewModelScope.launch {
            dataStoreUseCase.saveUserDetails(loginEntity)
        }
    }
}