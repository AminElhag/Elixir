package com.elixirgym.elixir.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.elixirgym.elixir.data.repository.ISessionManager
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

/**
 * SplashScreen checks for existing authentication on app startup.
 * If user is authenticated, navigates to CalendarScreen.
 * If not authenticated, navigates to HomeScreen.
 */
class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sessionManager: ISessionManager = koinInject()

        // Check authentication status on launch
        LaunchedEffect(Unit) {
            // Small delay to show splash screen (optional, can be removed)
            delay(500)

            // Check if user is authenticated
            val isAuthenticated = sessionManager.isUserAuthenticated()

            // Navigate based on authentication status
            if (isAuthenticated) {
                // User is logged in, go directly to CalendarScreen
                navigator.replaceAll(CalendarScreen())
            } else {
                // User is not logged in, go to HomeScreen
                navigator.replaceAll(HomeScreen())
            }
        }

        // Show loading screen while checking authentication
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Elixir Gym",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(32.dp))

                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading...",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
