package com.pocket.usermanagement.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pocket.usermanagement.R
import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val userProfile by profileViewModel.userProfile.collectAsState(null)
    var firstName by remember { mutableStateOf("") }
    var maidenName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current


    // Reload profile data when the screen becomes visible,
    // in case it was updated in a sub-screen.
    LaunchedEffect(Unit) {
        profileViewModel.getUserProfile()

    }

    LaunchedEffect(userProfile) {
        userProfile?.let { profile ->
            firstName = profile.firstName ?: ""
            maidenName = profile.maidenName ?: ""
            lastName = profile.lastName ?: ""
            email = profile.email ?: ""
            birthDate = profile.birthDate ?: ""
            phone = profile.phone ?: ""
            gender = profile.gender ?: ""
            weight = profile.weight.toString()
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(16.dp))
            .verticalScroll(rememberScrollState())
            .padding(bottom = 80.dp), // Space for the bottom button
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(userProfile = userProfile, onImageClick = { })
        ProfileTextField(
            value = firstName,
            label = stringResource(id = R.string.str_first_name),
            onValueChange = {
                firstName = it
            },
            keyboardType = KeyboardType.Text
        )
        ProfileTextField(
            value = maidenName,
            label = stringResource(id = R.string.str_maiden_name),
            onValueChange = {
                maidenName = it
            },
            keyboardType = KeyboardType.Text
        )
        ProfileTextField(
            value = lastName,
            label = stringResource(id = R.string.str_last_name),
            onValueChange = {
                lastName = it
            },
            keyboardType = KeyboardType.Text
        )

        ProfileTextField(
            value = email,
            label = stringResource(id = R.string.str_email),
            onValueChange = {
                email = it
            },
            keyboardType = KeyboardType.Email
        )
    }
}


@Composable
fun ProfileHeader(userProfile: UserProfileEntity?, onImageClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(contentAlignment = Alignment.BottomEnd) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userProfile?.image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.user_profile_image_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            IconButton(
                onClick = onImageClick,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,

                    contentDescription = stringResource(R.string.change_profile_image_desc),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${userProfile?.firstName ?: ""} ${userProfile?.lastName ?: userProfile?.username}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun ProfileTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        )
    )
}



