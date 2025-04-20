package com.pocket.usermanagement.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pocket.usermanagement.features.login.ui.LoginViewModel
import com.pocket.usermanagement.main.UserManagementApplication

object AppViewModelFactory {

    val LoginFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {

            val app = (this[APPLICATION_KEY] as UserManagementApplication).userManagementContainer
            val loginUseCase =
                app.provideLoginUseCase(app.provideLoginRepository(app.provideUserManagementApiService()))
            LoginViewModel(
                loginUseCase = loginUseCase

            )
        }
    }
}
