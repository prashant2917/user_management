package com.pocket.usermanagement.datastore.data

import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveUserDetails(loginEntity: LoginEntity?)
    suspend fun getUserFullName(): Flow<String>
}