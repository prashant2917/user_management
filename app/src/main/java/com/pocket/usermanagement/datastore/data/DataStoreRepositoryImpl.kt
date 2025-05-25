package com.pocket.usermanagement.datastore.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.pocket.usermanagement.features.login.data.entity.LoginEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreRepository {
    override suspend fun saveUserDetails(loginEntity: LoginEntity?) {
        dataStore.edit { preferences ->
            preferences[DatastoreKeys.USER_NAME] = loginEntity?.username ?: ""
            preferences[DatastoreKeys.USER_EMAIL] = loginEntity?.email ?: ""
            preferences[DatastoreKeys.USER_FIRST_NAME] = loginEntity?.firstName ?: ""
            preferences[DatastoreKeys.USER_LAST_NAME] = loginEntity?.lastName ?: ""
            preferences[DatastoreKeys.USER_IMAGE] = loginEntity?.image ?: ""

        }
    }

    override suspend fun getUserFullName(): Flow<String> {
        return dataStore.data.map {

            val firstName = it[DatastoreKeys.USER_FIRST_NAME] ?: ""
            val lastName = it[DatastoreKeys.USER_LAST_NAME] ?: ""
            return@map "$firstName $lastName"
        }
    }
}