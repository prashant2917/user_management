package com.pocket.usermanagement.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pocket.usermanagement.utils.AppLogger

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val userId = homeViewModel.mutableStateFlowUserId.collectAsState()
    AppLogger.d("HomeScreen", "userId ${userId.value}")
    val userProfileResponseEntity by homeViewModel.mutableStateFlowProfileSuccess.collectAsState()
    val userProfileError by homeViewModel.mutableStateFlowProfileError.collectAsState()
    val isLoading by homeViewModel.mutableStateFlowLoading.collectAsState()

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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = userProfileResponseEntity?.username.toString())
        }
    }
}