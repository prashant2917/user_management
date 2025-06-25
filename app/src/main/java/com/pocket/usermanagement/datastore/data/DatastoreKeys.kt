package com.pocket.usermanagement.datastore.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DatastoreKeys {
    val USER_ID = stringPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val IS_LOGIN = booleanPreferencesKey("is_login")
    val IS_EDIT_USER = booleanPreferencesKey("is_edit_user")

}