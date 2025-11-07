package com.pocket.usermanagement.features.login.domain

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import com.pocket.usermanagement.features.login.data.model.LoginRequest
import com.pocket.usermanagement.features.login.domain.LoginRepository
import com.pocket.usermanagement.utils.ResultEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor (private val loginRepository: LoginRepository) {
    suspend fun userLogin(loginRequest: LoginRequest): Flow<ResultEvent<LoginEntity?>> {
        return loginRepository.userLogin(loginRequest)
    }
}
