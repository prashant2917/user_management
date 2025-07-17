package com.pocket.usermanagement.datastore.domain

import com.pocket.usermanagement.datastore.data.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreUseCase @Inject constructor(private val dataStoreRepository: DataStoreRepository) {

    suspend fun saveUserId(userId: String?) {
        dataStoreRepository.saveUserId(userId)
    }

    suspend fun getUserId(): Flow<String> {
        return dataStoreRepository.getUserId()
    }

    suspend fun setIsUserLogin(isUserLogin: Boolean) {
        dataStoreRepository.setIsUserLogin(isUserLogin)
    }

    suspend fun getIsUserLogin(): Flow<Boolean> {
        return dataStoreRepository.getIsUserLogin()
    }
}