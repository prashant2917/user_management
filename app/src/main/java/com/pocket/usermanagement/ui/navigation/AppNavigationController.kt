package com.pocket.usermanagement.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pocket.usermanagement.R
import com.pocket.usermanagement.features.home.ui.HomeScreen
import com.pocket.usermanagement.features.login.ui.LoginScreen
import com.pocket.usermanagement.features.profile.ui.ProfileScreen

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

        composable(route = AppNavigationScreen.USER_PROFILE.name) {
            ProfileScreen(navController, profileViewModel = hiltViewModel())
        }
    }
}


enum class AppNavigationScreen {
    LOGIN,
    HOME,
    USER_PROFILE,
    USER_LIST
}

val noBackButtonRoutes = setOf(AppNavigationScreen.LOGIN.name, AppNavigationScreen.HOME.name)

fun String?.getScreenTitle(context: Context): String {
    return when (this) {
        AppNavigationScreen.LOGIN.name -> context.getString(R.string.str_login)
        AppNavigationScreen.HOME.name -> context.getString(R.string.home_title)
        AppNavigationScreen.USER_PROFILE.name -> context.getString(R.string.profile_screen_title)
        AppNavigationScreen.USER_LIST.name -> context.getString(R.string.user_list_title)
        else -> context.getString(R.string.app_name)
    }
}

