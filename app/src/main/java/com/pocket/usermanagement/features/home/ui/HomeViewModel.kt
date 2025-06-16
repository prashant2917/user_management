package com.pocket.usermanagement.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.datastore.data.domain.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val dataStoreUseCase: DataStoreUseCase) :
    ViewModel() {

    init {
        getUserFullName()
    }

    private val _mutableStateFlowUserFullName = MutableStateFlow<String?>("")
    val mutableStateFlowUserFullName = _mutableStateFlowUserFullName.asStateFlow()

    fun getUserFullName() {
        viewModelScope.launch {
            dataStoreUseCase.getUserFullName().collect { fullName ->
                _mutableStateFlowUserFullName.updateAndGet {
                    fullName
                }
            }
        }

    }

}