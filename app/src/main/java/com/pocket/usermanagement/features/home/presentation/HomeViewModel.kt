package com.pocket.usermanagement.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.datastore.domain.DataStoreUseCase
import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.features.profile.domain.GetProfileUseCase
import com.pocket.usermanagement.main.ApplicationDataSource
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

sealed class HomeScreenEvent {
    data class HomeScreenSuccess(val userEntity: UserProfileEntity?) : HomeScreenEvent()
    data class HomeScreenError(val exception: Exception) : HomeScreenEvent()

}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val getProfileUseCase: GetProfileUseCase
) :
    ViewModel() {
    init {
        getUserId()
    }

    private val _mutableStateFlowUserId = MutableStateFlow<String>("")
    val mutableStateFlowUserId = _mutableStateFlowUserId.asStateFlow()

    //private val _mutableStateFlowProfileSuccess = MutableStateFlow<UserProfileEntity?>(null)
    //var mutableStateFlowProfileSuccess = _mutableStateFlowProfileSuccess.asStateFlow()

   // private val _mutableStateFlowProfileException = MutableStateFlow<Exception?>(null)
    //val mutableStateFlowProfileException = _mutableStateFlowProfileException.asStateFlow()

    private val _mutableSharedFlowHomeScreenEvent = MutableSharedFlow<HomeScreenEvent>()
    val mutableSharedFlowHomeScreenEvent = _mutableSharedFlowHomeScreenEvent.asSharedFlow()

    private val _mutableStateFlowLoading = MutableStateFlow(false)
    val mutableStateFlowLoading = _mutableStateFlowLoading.asStateFlow()

    private fun getUserId() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCase.getUserId().collect { userId ->
               userId.let {
                   _mutableStateFlowUserId.value = userId
               }

            }
        }
    }

    fun getUserProfile(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            resetValues()
            getProfileUseCase.getUserProfile(userId).collect { resultEvent ->
                when (resultEvent) {
                    is ResultEvent.Error -> {
                        AppLogger.d("Error ${resultEvent.exception.message}")
                        _mutableStateFlowLoading.value = false
                       // _mutableStateFlowProfileException.value = resultEvent.exception
                        _mutableSharedFlowHomeScreenEvent.emit(HomeScreenEvent.HomeScreenError(resultEvent.exception))
                    }

                    ResultEvent.Loading -> {
                        AppLogger.d("Loading")
                        _mutableStateFlowLoading.value = true
                    }

                    is ResultEvent.Success -> {
                        AppLogger.d("Success ${resultEvent.data}")
                        _mutableStateFlowLoading.value = false
                       // _mutableStateFlowProfileSuccess.value = resultEvent.data
                        _mutableSharedFlowHomeScreenEvent.emit(HomeScreenEvent.HomeScreenSuccess(resultEvent.data))
                    }
                }
            }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun resetValues() {
      //  _mutableStateFlowProfileSuccess.value = null
       // _mutableStateFlowProfileException.value = null
        _mutableStateFlowLoading.value = false
        _mutableSharedFlowHomeScreenEvent.resetReplayCache()
    }

    fun saveUserProfile(userProfileEntity: UserProfileEntity?) {

        ApplicationDataSource.setUserProfileEntity(userProfileEntity)
    }

}