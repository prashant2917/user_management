package com.pocket.usermanagement.datastore.data.domain

import com.pocket.usermanagement.datastore.data.DataStoreRepository
import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend fun saveUserDetails(loginEntity: LoginEntity?) {
        dataStoreRepository.saveUserDetails(loginEntity)
    }

    suspend fun getUserFullName(): Flow<String> {
        return dataStoreRepository.getUserFullName()
    }
}