package com.compose_app.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.compose_app.ui.component.BackArrowIcon

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    BackArrowIcon()
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JD",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Name
            Text(
                text = "John Doe",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Text(
                text = "john.doe@example.com",
                fontSize = 16.sp,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Profile Info Cards
            ProfileInfoCard(
                title = "Account",
                items = listOf(
                    "Edit Profile" to {},
                    "Change Password" to {},
                    "Privacy Settings" to {}
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileInfoCard(
                title = "Preferences",
                items = listOf(
                    "Notifications" to {},
                    "Language" to {},
                    "Theme" to {}
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Out Button
            Button(
                onClick = { /* Handle sign out */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error
                )
            ) {
                Text("Sign Out")
            }
        }
    }
}

@Composable
fun ProfileInfoCard(
    title: String,
    items: List<Pair<String, () -> Unit>>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            items.forEachIndexed { index, (itemTitle, onClick) ->
                TextButton(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = itemTitle,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (index < items.size - 1) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}