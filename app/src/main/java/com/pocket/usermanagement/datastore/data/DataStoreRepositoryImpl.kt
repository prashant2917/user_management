package com.pocket.usermanagement.datastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreRepository {
    override suspend fun saveUserId(userId: String?) {
        dataStore.edit { preferences ->
            preferences[DatastoreKeys.USER_ID] = userId ?: ""

        }
    }

    override suspend fun getUserId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[DatastoreKeys.USER_ID] ?: ""
        }
    }

    override suspend fun setIsUserLogin(isUserLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[DatastoreKeys.IS_LOGIN] = isUserLogin
        }
    }

    override suspend fun getIsUserLogin(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[DatastoreKeys.IS_LOGIN] ?: false
        }
    }
}