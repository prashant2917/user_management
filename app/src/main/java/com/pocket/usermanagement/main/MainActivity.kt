package com.pocket.usermanagement.main


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pocket.usermanagement.main.MainActivity.Companion.KEEP_SPLASH
import com.pocket.usermanagement.main.MainActivity.Companion.SPLASH_DELAY
import com.pocket.usermanagement.ui.AppHeader
import com.pocket.usermanagement.ui.navigation.AppNavigationController
import com.pocket.usermanagement.ui.navigation.getScreenTitle
import com.pocket.usermanagement.ui.navigation.noBackButtonRoutes
import com.pocket.usermanagement.ui.theme.UserManagementTheme
import com.pocket.usermanagement.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        setUpSplashScreen(splashScreen)
        setContent {
            UserManagementApp()

        }
    }

    companion object {
        var KEEP_SPLASH = true
        const val SPLASH_DELAY = 1000L
    }
}

private fun setUpSplashScreen(splashScreen: SplashScreen) {
    splashScreen.setKeepOnScreenCondition { KEEP_SPLASH }
    Handler(Looper.getMainLooper()).postDelayed({
        KEEP_SPLASH = false
    }, SPLASH_DELAY)
}


@Composable
fun UserManagementApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    UserManagementTheme {
        Scaffold(
            topBar = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val appBarTitle = currentRoute.getScreenTitle(context)
                AppLogger.d("currentRoute $currentRoute")

                val showBackButton =
                    currentRoute != null && currentRoute !in noBackButtonRoutes && navController.previousBackStackEntry != null
                AppLogger.d("showBackButton $showBackButton")

                AppHeader(
                    title = appBarTitle,
                    navController = navController,
                    showBackButton = showBackButton
                )
            }
        )

        { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                //content here
                AppNavigationController(
                    navController = navController,
                    appNavigationViewModel = hiltViewModel()
                )
            }
        }

    }

}
