package com.pocket.usermanagement.datastore.domain

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveUserId(userId: String?)
    suspend fun getUserId(): Flow<String>
    suspend fun setIsUserLogin(isUserLogin: Boolean)
    suspend fun getIsUserLogin(): Flow<Boolean>
}