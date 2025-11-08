package com.elixirgym.elixir.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Elixir Gym") }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to Elixir Gym",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Your trainer booking app",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navigator.push(TrainerListScreen()) }
                ) {
                    Text("Browse Trainers")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navigator.push(BookingsScreen()) }
                ) {
                    Text("My Bookings")
                }
            }
        }
    }
}
