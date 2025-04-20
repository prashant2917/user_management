package com.pocket.usermanagement.main

import android.app.Application
import com.pocket.usermanagement.di.UserManagementContainer

class UserManagementApplication: Application() {
  lateinit var userManagementContainer: UserManagementContainer
    override fun onCreate() {
        super.onCreate()
        userManagementContainer = UserManagementContainer()
    }

}