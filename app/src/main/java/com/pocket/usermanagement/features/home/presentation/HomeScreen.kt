package com.pocket.usermanagement.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.network.ApiErrorException
import com.pocket.usermanagement.utils.AppLogger
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val userId = homeViewModel.mutableStateFlowUserId.collectAsState()
    AppLogger.d("HomeScreen", "userId ${userId.value}")
    var userProfileResponseEntity: UserProfileEntity? by remember { mutableStateOf(null) }
    var userProfileError by remember { mutableStateOf("") }
    var userProfileException: Exception? by remember { mutableStateOf(null) }
    val isLoading by homeViewModel.mutableStateFlowLoading.collectAsState()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val homeScreenEvent by homeViewModel.mutableSharedFlowHomeScreenEvent.collectAsState(null)

    LaunchedEffect(key1 = userId) {
        // Ensure userId is valid before making the API call
        if (userId.value.isNotEmpty()) { // Or any other validation like userId != null
            AppLogger.d(
                "HomeScreen", "LaunchedEffect triggered. Fetching profile for userId: $userId"
            )
            homeViewModel.getUserProfile(userId.value)
        }
    }

    LaunchedEffect(key1 = homeScreenEvent) {
        launch {
            homeScreenEvent.let { event ->
                when (event) {
                    is HomeScreenEvent.HomeScreenSuccess -> {
                        userProfileResponseEntity = event.userEntity
                        homeViewModel.saveUserProfile(event.userEntity)
                    }

                    is HomeScreenEvent.HomeScreenError -> {
                        userProfileError =
                            ApiErrorException.getErrorMessage(context, event.exception)
                        userProfileException = event.exception
                        if (userProfileError.isNotEmpty()) {
                            snackBarHostState.showSnackbar(
                                message = userProfileError,
                                duration = SnackbarDuration.Long,
                                actionLabel = context.getString(R.string.str_dismiss),
                                withDismissAction = true
                            )

                        }
                    }

                    else -> {
                        AppLogger.d("Nothing to do")
                    }
                }
            }
        }

    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Additional padding for content if needed
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top // Changed to Top to show card at the top
            ) {

                if (isLoading) {
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator()
                    Text(
                        text = stringResource(R.string.loading_profile),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }


                userProfileResponseEntity?.let { profileEntity ->
                    UserProfileCard(
                        userProfile = profileEntity, navController = navController
                    )

                }

            }
        }
    }

}

