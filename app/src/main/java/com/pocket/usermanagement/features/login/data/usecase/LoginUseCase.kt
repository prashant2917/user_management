package com.pocket.usermanagement.features.login.data.usecase

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.data.repository.LoginRepository
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val loginRepository: LoginRepository) {
    suspend fun userLogin(loginRequest: LoginRequest): Flow<ResultEvent<LoginEntity?>> {
        return loginRepository.userLogin(loginRequest)
    }
}
