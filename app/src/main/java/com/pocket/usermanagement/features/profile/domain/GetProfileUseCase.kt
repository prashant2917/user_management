package com.pocket.usermanagement.features.profile.domain

import com.pocket.usermanagement.features.profile.data.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    suspend fun getUserProfile(userId: String) = profileRepository.getUserProfile(userId)

}