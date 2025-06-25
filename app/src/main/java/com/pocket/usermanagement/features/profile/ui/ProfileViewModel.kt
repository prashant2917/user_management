package com.pocket.usermanagement.features.profile.ui

import androidx.lifecycle.ViewModel
import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.features.profile.data.repository.ProfileRepository
import com.pocket.usermanagement.main.ApplicationDataSource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository) :
    ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfileEntity?>(null)
    val userProfile: StateFlow<UserProfileEntity?> = _userProfile.asStateFlow()

    fun getUserProfile() {
        val userProfile = ApplicationDataSource.getUserProfileEntity()
        _userProfile.value = userProfile

    }

    /* fun getBasicDetails(): String? {

     }*/

}