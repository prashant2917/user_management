package com.pocket.usermanagement.features.login.data.repository

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun userLogin(loginRequest: LoginRequest): Flow<ResultEvent<LoginEntity?>>
}