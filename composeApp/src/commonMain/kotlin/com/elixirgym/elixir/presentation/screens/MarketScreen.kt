package com.elixirgym.elixir.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import com.elixirgym.elixir.data.SampleProjectData
import com.elixirgym.elixir.domain.model.Project
import com.elixirgym.elixir.domain.model.ProjectCategory
import com.elixirgym.elixir.presentation.components.BottomToolbar

class MarketScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val projects = SampleProjectData.getProjects()
        var selectedCategory by remember { mutableStateOf<ProjectCategory?>(null) }

        val filteredProjects = if (selectedCategory != null) {
            projects.filter { it.category == selectedCategory }
        } else {
            projects
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Market") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = {
                BottomToolbar()
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Category Filter Chips
                CategoryFilterSection(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )

                // Projects List
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredProjects) { project ->
                        ProjectCard(
                            project = project,
                            onClick = {
                                navigator.push(ProjectDetailsScreen(project = project))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilterSection(
    selectedCategory: ProjectCategory?,
    onCategorySelected: (ProjectCategory?) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { onCategorySelected(null) },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedCategory == ProjectCategory.PILATES,
                    onClick = {
                        onCategorySelected(
                            if (selectedCategory == ProjectCategory.PILATES) null
                            else ProjectCategory.PILATES
                        )
                    },
                    label = { Text("Pilates") }
                )
                FilterChip(
                    selected = selectedCategory == ProjectCategory.YOGA,
                    onClick = {
                        onCategorySelected(
                            if (selectedCategory == ProjectCategory.YOGA) null
                            else ProjectCategory.YOGA
                        )
                    },
                    label = { Text("Yoga") }
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == ProjectCategory.PERSONAL_TRAINING,
                    onClick = {
                        onCategorySelected(
                            if (selectedCategory == ProjectCategory.PERSONAL_TRAINING) null
                            else ProjectCategory.PERSONAL_TRAINING
                        )
                    },
                    label = { Text("Personal Training") }
                )
                FilterChip(
                    selected = selectedCategory == ProjectCategory.GROUP_TRAINING,
                    onClick = {
                        onCategorySelected(
                            if (selectedCategory == ProjectCategory.GROUP_TRAINING) null
                            else ProjectCategory.GROUP_TRAINING
                        )
                    },
                    label = { Text("Group Training") }
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == ProjectCategory.NUTRITION,
                    onClick = {
                        onCategorySelected(
                            if (selectedCategory == ProjectCategory.NUTRITION) null
                            else ProjectCategory.NUTRITION
                        )
                    },
                    label = { Text("Nutrition") }
                )
                FilterChip(
                    selected = selectedCategory == ProjectCategory.WELLNESS,
                    onClick = {
                        onCategorySelected(
                            if (selectedCategory == ProjectCategory.WELLNESS) null
                            else ProjectCategory.WELLNESS
                        )
                    },
                    label = { Text("Wellness") }
                )
            }
        }
    }
}

@Composable
fun ProjectCard(
    project: Project,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Project Image
            AsyncImage(
                model = project.imageUrl,
                contentDescription = project.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )

            // Project Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Project Name
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Project Description
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Project Info Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Number of Classes
                    InfoItem(
                        icon = Icons.Default.Event,
                        text = "${project.numberOfClasses} Classes"
                    )

                    // Duration per Class
                    InfoItem(
                        icon = Icons.Default.AccessTime,
                        text = "${project.durationPerClass} min"
                    )

                    // Validity Period
                    InfoItem(
                        icon = Icons.Default.CalendarMonth,
                        text = "${project.validityPeriod} days"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Divider()

                Spacer(modifier = Modifier.height(12.dp))

                // Price and Purchase Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Price",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${String.format("%.2f", project.price)} ${project.currency}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Button(
                        onClick = onClick
                    ) {
                        Text("View Details")
                    }
                }
            }
        }
    }
}

@Composable
fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
