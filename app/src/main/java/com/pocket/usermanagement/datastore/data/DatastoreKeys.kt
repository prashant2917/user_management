package com.pocket.usermanagement.datastore.data

import androidx.datastore.preferences.core.stringPreferencesKey

object DatastoreKeys {
    val USER_IMAGE = stringPreferencesKey("user_image")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
    val USER_LAST_NAME = stringPreferencesKey("user_last_name")

}