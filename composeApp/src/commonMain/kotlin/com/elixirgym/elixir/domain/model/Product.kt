package com.elixirgym.elixir.domain.model

/**
 * Represents a purchasable product/package (e.g., training packages)
 */
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val currency: String = "SAR",
    val numberOfClasses: Int,
    val category: ProductCategory,
    val trainer: String? = null, // Optional trainer for the package
    val durationPerClass: Int = 60, // Duration in minutes
    val validityPeriod: Int = 90, // Validity in days
    val features: List<String> = emptyList()
)

enum class ProductCategory {
    PILATES,
    YOGA,
    PERSONAL_TRAINING,
    GROUP_TRAINING,
    NUTRITION,
    WELLNESS,
    OTHER
}
