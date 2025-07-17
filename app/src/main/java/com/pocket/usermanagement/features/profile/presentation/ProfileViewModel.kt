package com.pocket.usermanagement.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.features.profile.data.repository.ProfileRepository
import com.pocket.usermanagement.main.ApplicationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {

    private val _userProfile = MutableSharedFlow<UserProfileEntity?>()
    val userProfile = _userProfile.asSharedFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            val userProfile = ApplicationDataSource.getUserProfileEntity()
            _userProfile.emit(userProfile)
        }

    }
}