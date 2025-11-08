package com.elixirgym.elixir.domain.model

data class TrainingType(
    val id: String,
    val name: String,
    val duration: Int  // Duration in minutes
)

data class Trainer(
    val id: String,
    val name: String,
    val photoUrl: String,
    val shortDescription: String,
    val specialization: String,
    val rating: Float,
    val comments: List<TrainerComment>,
    val trainingTypes: List<TrainingType>
)

data class TrainerComment(
    val id: String,
    val memberName: String,
    val memberPhotoUrl: String?,
    val comment: String,
    val rating: Float,
    val date: String
)
