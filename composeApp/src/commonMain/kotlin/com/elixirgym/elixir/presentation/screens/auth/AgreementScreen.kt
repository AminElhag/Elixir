package com.elixirgym.elixir.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.elixirgym.elixir.presentation.viewmodels.AccountCreationViewModel

class AgreementScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<AccountCreationViewModel>()
        var agreedToTerms by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Terms & Conditions") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Scrollable terms content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header Icon
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Please Review Our Terms",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "By creating an account, you agree to our terms and conditions. Please read them carefully.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Terms Content
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            TermsSection(
                                title = "1. Account Information",
                                content = "You agree to provide accurate and complete information during registration. You are responsible for maintaining the confidentiality of your account credentials."
                            )

                            TermsSection(
                                title = "2. Privacy & Data Protection",
                                content = "We collect and process your personal data including name, email, phone number, ID number, address, and medical information in accordance with applicable privacy laws. Your medical information will be kept strictly confidential and used only for training safety purposes."
                            )

                            TermsSection(
                                title = "3. Health & Safety",
                                content = "You confirm that the medical information provided is accurate and complete. It is your responsibility to inform your trainer of any health conditions that may affect your training. Elixir Gym and its trainers are not liable for health issues arising from undisclosed medical conditions."
                            )

                            TermsSection(
                                title = "4. Trainer Sessions",
                                content = "All bookings are subject to trainer availability. Cancellations must be made at least 24 hours in advance. Late cancellations may result in charges as per gym policy."
                            )

                            TermsSection(
                                title = "5. Code of Conduct",
                                content = "You agree to maintain respectful behavior towards trainers, staff, and other members. Elixir Gym reserves the right to terminate accounts for violations of gym policies or inappropriate conduct."
                            )

                            TermsSection(
                                title = "6. Liability Waiver",
                                content = "You acknowledge that physical training involves inherent risks. By participating, you accept these risks and waive claims against Elixir Gym, its trainers, and staff for injuries that may occur during training sessions."
                            )

                            TermsSection(
                                title = "7. Payment & Fees",
                                content = "You agree to pay all fees associated with your bookings. Prices are subject to change with reasonable notice. Refunds are provided in accordance with our cancellation policy."
                            )

                            TermsSection(
                                title = "8. Modification of Terms",
                                content = "Elixir Gym reserves the right to modify these terms at any time. Continued use of the service after modifications constitutes acceptance of the updated terms."
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Bottom section with checkbox and button
                Surface(
                    tonalElevation = 3.dp,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Agreement Checkbox
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = agreedToTerms,
                                onCheckedChange = { agreedToTerms = it }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "I have read and agree to the Terms & Conditions",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Accept Button
                        Button(
                            onClick = {
                                viewModel.agreeToTerms()
                                navigator.push(OTPVerificationScreen())
                            },
                            enabled = agreedToTerms,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Accept & Continue",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TermsSection(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
