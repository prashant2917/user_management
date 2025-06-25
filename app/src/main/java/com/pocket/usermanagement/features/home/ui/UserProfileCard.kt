package com.pocket.usermanagement.features.home.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.pocket.usermanagement.R
import com.pocket.usermanagement.features.profile.data.entity.UserProfileEntity
import com.pocket.usermanagement.ui.navigation.AppNavigationScreen

// Define UserRoles if you haven't elsewhere
object UserRoles {
    const val ADMIN = "admin"
    const val MODERATOR = "moderator"
    const val USER = "user"
}

@Composable
fun UserProfileCard(
    userProfile: UserProfileEntity?,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val fullName = userProfile?.firstName + " " + userProfile?.lastName

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // User Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userProfile?.image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.user_profile_image_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
            // Fallback if AsyncImage is not preferred or for simpler cases:
            // Image(
            //     painter = painterResource(id = R.drawable.ic_profile_placeholder), // Replace with actual image loading logic
            //     contentDescription = stringResource(R.string.user_profile_image_desc),
            //     modifier = Modifier
            //         .size(100.dp)
            //         .clip(CircleShape)
            // )

            Spacer(modifier = Modifier.height(16.dp))

            // Full Name
            Text(
                text = fullName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email ID
            Text(
                text = userProfile?.email ?: stringResource(R.string.not_available),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Role and Description
            val roleDescription = when (userProfile?.role?.lowercase()) {
                UserRoles.ADMIN -> stringResource(R.string.role_desc_admin)
                UserRoles.MODERATOR -> stringResource(R.string.role_desc_moderator)
                UserRoles.USER -> stringResource(R.string.role_desc_user)
                else -> stringResource(R.string.role_desc_unknown)
            }
            Text(
                text = "${stringResource(R.string.role_label)}: ${
                    userProfile?.role?.replaceFirstChar { it.uppercase() } ?: stringResource(
                        R.string.unknown
                    )
                }",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = roleDescription,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Action Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // View/Edit Profile Button (All Roles)
                Button(
                    onClick = { navController.navigate(AppNavigationScreen.USER_PROFILE.name) }, // Assuming profile screen takes userId
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.str_view_edit_profile))
                }

                // Role-Specific Buttons
                when (userProfile?.role?.lowercase()) {
                    UserRoles.ADMIN -> {
                        Button(
                            onClick = { navController.navigate(AppNavigationScreen.USER_LIST.name) }, // Navigate to user list
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.str_modify_users))
                        }

                        Button(
                            onClick = { navController.navigate(AppNavigationScreen.USER_LIST.name) }, // Navigate to user list
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.str_add_user))
                        }
                    }

                    UserRoles.MODERATOR -> {
                        Button(
                            onClick = { navController.navigate(AppNavigationScreen.USER_LIST.name) }, // Navigate to user list
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.str_view_users))
                        }
                    }
                    // User role has no additional button here as per requirements
                }
            }
        }
    }
}
