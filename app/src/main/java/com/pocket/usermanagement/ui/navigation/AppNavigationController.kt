package com.pocket.usermanagement.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pocket.usermanagement.features.home.ui.HomeScreen
import com.pocket.usermanagement.features.login.ui.LoginScreen

@Composable
fun AppNavigationController(
    navController: NavHostController,
    appNavigationViewModel: AppNavigationViewModel
) {

    val isUserLogin by appNavigationViewModel.mutableStateFlowUserLogin.collectAsState()
    NavHost(
        navController = navController,
        startDestination = if (isUserLogin) AppNavigationScreen.HOME.name else AppNavigationScreen.LOGIN.name
    ) {
        composable(route = AppNavigationScreen.LOGIN.name) {
            LoginScreen(navController = navController, loginViewModel = hiltViewModel())
        }

        composable(route = AppNavigationScreen.HOME.name) {
            HomeScreen(navController = navController, homeViewModel = hiltViewModel())
        }
    }
}


enum class AppNavigationScreen {
    LOGIN,
    HOME,
    USER_PROFILE,
    USER_LIST
}

