package com.pocket.usermanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pocket.usermanagement.features.login.ui.LoginScreen
import com.pocket.usermanagement.features.login.ui.LoginViewModel

@Composable
fun AppNavigationController(navController: NavHostController) {

    NavHost(navController = navController, startDestination = AppNavigationScreen.LOGIN.name) {
       composable(route = AppNavigationScreen.LOGIN.name) {
           LoginScreen(navController = navController)
       }
  }
}


enum class AppNavigationScreen {
    LOGIN,
    HOME,
    USER_PROFILE,
    USER_POST,
    USER_CART
}

