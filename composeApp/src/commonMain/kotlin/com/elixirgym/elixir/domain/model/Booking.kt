package com.elixirgym.elixir.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

enum class BookingStatus {
    CURRENT,    // Currently active booking
    UPCOMING,   // Future booking
    CANCELLED   // Cancelled booking
}

data class Booking(
    val id: String,
    val trainerId: String,
    val trainerName: String,
    val trainerPhotoUrl: String,
    val date: LocalDate,
    val time: LocalTime,
    val status: BookingStatus,
    val sessionType: String,  // e.g., "Personal Training", "Yoga", etc.
    val duration: Int         // Duration in minutes
)
