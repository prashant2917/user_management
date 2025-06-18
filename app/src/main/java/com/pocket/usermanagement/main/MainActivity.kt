package com.pocket.usermanagement.main


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.pocket.usermanagement.main.MainActivity.Companion.KEEP_SPLASH
import com.pocket.usermanagement.main.MainActivity.Companion.SPLASH_DELAY
import com.pocket.usermanagement.ui.AppHeader
import com.pocket.usermanagement.ui.navigation.AppNavigationController
import com.pocket.usermanagement.ui.navigation.AppNavigationScreen
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
    val appBarTitle by remember { mutableStateOf("") }
    UserManagementTheme {
        Scaffold(
            topBar = {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                AppLogger.d("currentRoute $currentRoute")

                val showBackButton = if (currentRoute != null) {
                    currentRoute != AppNavigationScreen.LOGIN.name
                } else false
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
                AppNavigationController(navController = navController)
            }
        }

    }
}
