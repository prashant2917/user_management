package com.pocket.usermanagement.features.login.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.datastore.domain.DataStoreUseCase
import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.domain.LoginUseCase
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.ResultEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginScreenEvent {
    data class LoginScreenSuccess(val userEntity: LoginEntity?) : LoginScreenEvent()
    data class LoginScreenError(val exception: Exception) : LoginScreenEvent()

}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {


    private val _loginScreenEventFlow = MutableSharedFlow<LoginScreenEvent>() // replay = 0 by default
    val loginEventFlow = _loginScreenEventFlow.asSharedFlow()

    private val _mutableStateFlowLoading = MutableStateFlow(false)
    val mutableStateFlowLoading = _mutableStateFlowLoading.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun resetValues() {

        _mutableStateFlowLoading.value = false
        _loginScreenEventFlow.resetReplayCache()
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
                        _loginScreenEventFlow.emit(LoginScreenEvent.LoginScreenError(resultEvent.exception))

                    }

                    is ResultEvent.Success -> {
                        AppLogger.d("Success ${resultEvent.data}")
                        _mutableStateFlowLoading.value = false
                        _loginScreenEventFlow.emit(LoginScreenEvent.LoginScreenSuccess(resultEvent.data))
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