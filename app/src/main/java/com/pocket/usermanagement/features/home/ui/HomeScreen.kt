package com.pocket.usermanagement.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pocket.usermanagement.R
import com.pocket.usermanagement.network.ApiErrorException
import com.pocket.usermanagement.utils.AppLogger
import com.pocket.usermanagement.utils.UiUtils

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val userId = homeViewModel.mutableStateFlowUserId.collectAsState()
    AppLogger.d("HomeScreen", "userId ${userId.value}")
    val userProfileResponseEntity by homeViewModel.mutableStateFlowProfileSuccess.collectAsState()
    var userProfileError by remember { mutableStateOf("") }
    val userProfileException by homeViewModel.mutableStateFlowProfileException.collectAsState()
    val isLoading by homeViewModel.mutableStateFlowLoading.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = userId) {
        // Ensure userId is valid before making the API call
        if (userId.value.isNotEmpty()) { // Or any other validation like userId != null
            AppLogger.d(
                "HomeScreen",
                "LaunchedEffect triggered. Fetching profile for userId: $userId"
            )
            homeViewModel.getUserProfile(userId.value)
        }
    }
    when {
        userProfileException != null -> {
            userProfileError = ApiErrorException.getErrorMessage(context, userProfileException)
            if (userProfileError.isNotEmpty()) {
                UiUtils.ShowToast(context = context, message = userProfileError)

            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), // Additional padding for content if needed
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Changed to Top to show card at the top
        ) {
            when {
                isLoading -> {
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator()
                    Text(
                        text = stringResource(R.string.loading_profile),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                userProfileException != null -> {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = stringResource(
                            R.string.error_loading_profile,
                            userProfileException?.message ?: stringResource(R.string.unknown)
                        ),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                userProfileResponseEntity != null -> {
                    // Display User Profile Card
                    UserProfileCard(
                        userProfile = userProfileResponseEntity,
                        navController = navController
                    )

                }

                userId.value.isEmpty() && !isLoading -> { // Condition when userId is not yet available
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = stringResource(R.string.user_id_not_available),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                // Optional: A state for when userId is present but profile isn't loaded yet (and not loading/error)
                // else -> {
                //     Text("Preparing to load profile for $userId...")
                // }
            }
        }
    }
}

