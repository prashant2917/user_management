package com.pocket.usermanagement.features.profile.domain

import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUserProfile(userId: String): Flow<ResultEvent<UserProfileEntity?>>
}