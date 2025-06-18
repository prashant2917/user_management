package com.pocket.usermanagement.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.datastore.data.domain.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppNavigationViewModel @Inject constructor(private val dataStoreUseCase: DataStoreUseCase) :
    ViewModel() {

    private val _mutableStateFlowUserLogin = MutableStateFlow(false)
    val mutableStateFlowUserLogin = _mutableStateFlowUserLogin

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCase.getIsUserLogin().collect { isUserLogin ->
                _mutableStateFlowUserLogin.value = isUserLogin
            }
        }
    }


}