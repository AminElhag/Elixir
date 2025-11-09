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
import com.elixirgym.elixir.data.SampleProductData
import com.elixirgym.elixir.domain.model.Product
import com.elixirgym.elixir.domain.model.ProductCategory
import com.elixirgym.elixir.presentation.components.BottomToolbar

class MarketScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val products = SampleProductData.getProducts()
        var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }

        val filteredProducts = if (selectedCategory != null) {
            products.filter { it.category == selectedCategory }
        } else {
            products
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
                    items(filteredProducts) { project ->
                        ProductCard(
                            product = project,
                            onClick = {
                                navigator.push(ProductDetailsScreen(product = project))
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
    selectedCategory: ProductCategory?,
    onCategorySelected: (ProductCategory?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
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
                selected = selectedCategory == ProductCategory.PILATES,
                onClick = {
                    onCategorySelected(
                        if (selectedCategory == ProductCategory.PILATES) null
                        else ProductCategory.PILATES
                    )
                },
                label = { Text("Pilates") }
            )
            FilterChip(
                selected = selectedCategory == ProductCategory.YOGA,
                onClick = {
                    onCategorySelected(
                        if (selectedCategory == ProductCategory.YOGA) null
                        else ProductCategory.YOGA
                    )
                },
                label = { Text("Yoga") }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedCategory == ProductCategory.PERSONAL_TRAINING,
                onClick = {
                    onCategorySelected(
                        if (selectedCategory == ProductCategory.PERSONAL_TRAINING) null
                        else ProductCategory.PERSONAL_TRAINING
                    )
                },
                label = { Text("Personal Training") }
            )
            FilterChip(
                selected = selectedCategory == ProductCategory.GROUP_TRAINING,
                onClick = {
                    onCategorySelected(
                        if (selectedCategory == ProductCategory.GROUP_TRAINING) null
                        else ProductCategory.GROUP_TRAINING
                    )
                },
                label = { Text("Group Training") }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedCategory == ProductCategory.NUTRITION,
                onClick = {
                    onCategorySelected(
                        if (selectedCategory == ProductCategory.NUTRITION) null
                        else ProductCategory.NUTRITION
                    )
                },
                label = { Text("Nutrition") }
            )
            FilterChip(
                selected = selectedCategory == ProductCategory.WELLNESS,
                onClick = {
                    onCategorySelected(
                        if (selectedCategory == ProductCategory.WELLNESS) null
                        else ProductCategory.WELLNESS
                    )
                },
                label = { Text("Wellness") }
            )
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
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
                model = product.imageUrl,
                contentDescription = product.name,
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
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Project Description
                Text(
                    text = product.description,
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
                        text = "${product.numberOfClasses} Classes"
                    )

                    // Duration per Class
                    InfoItem(
                        icon = Icons.Default.AccessTime,
                        text = "${product.durationPerClass} min"
                    )

                    // Validity Period
                    InfoItem(
                        icon = Icons.Default.CalendarMonth,
                        text = "${product.validityPeriod} days"
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
                            text = "${String.format("%.2f", product.price)} ${product.currency}",
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
