package com.pocket.usermanagement.ui.navigation

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.datastore.domain.DataStoreUseCase
import com.pocket.usermanagement.network.NetworkLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppNavigationViewModel @Inject constructor(private val dataStoreUseCase: DataStoreUseCase,
    connectivityManager: ConnectivityManager
    ) :
    ViewModel() {

    private val _mutableStateFlowUserLogin = MutableStateFlow(false)
    val mutableStateFlowUserLogin = _mutableStateFlowUserLogin

    val networkLiveData = NetworkLiveData(connectivityManager)
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