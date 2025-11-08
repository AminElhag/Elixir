package com.elixirgym.elixir.data

import com.elixirgym.elixir.domain.model.Booking
import com.elixirgym.elixir.domain.model.BookingStatus
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.time.ExperimentalTime

object SampleBookingData {
    @OptIn(ExperimentalTime::class)
    fun getBookings(): List<Booking> {
        val today = kotlin.time.Clock.System.todayIn(TimeZone.currentSystemDefault())

        return listOf(
            // Current booking (today)
            Booking(
                id = "1",
                trainerId = "1",
                trainerName = "Sarah Johnson",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/women/44.jpg",
                date = today,
                time = LocalTime(10, 0),
                status = BookingStatus.CURRENT,
                sessionType = "Personal Training",
                duration = 60
            ),
            // Upcoming bookings
            Booking(
                id = "2",
                trainerId = "2",
                trainerName = "Mike Chen",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/men/32.jpg",
                date = today.plus(3, kotlinx.datetime.DateTimeUnit.DAY),
                time = LocalTime(14, 30),
                status = BookingStatus.UPCOMING,
                sessionType = "Strength Training",
                duration = 60
            ),
            Booking(
                id = "3",
                trainerId = "3",
                trainerName = "Emily Rodriguez",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/women/65.jpg",
                date = today.plus(5, kotlinx.datetime.DateTimeUnit.DAY),
                time = LocalTime(9, 0),
                status = BookingStatus.UPCOMING,
                sessionType = "Yoga",
                duration = 90
            ),
            Booking(
                id = "4",
                trainerId = "1",
                trainerName = "Sarah Johnson",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/women/44.jpg",
                date = today.plus(7, kotlinx.datetime.DateTimeUnit.DAY),
                time = LocalTime(16, 0),
                status = BookingStatus.UPCOMING,
                sessionType = "Personal Training",
                duration = 60
            ),
            Booking(
                id = "5",
                trainerId = "4",
                trainerName = "David Kim",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/men/22.jpg",
                date = today.plus(10, kotlinx.datetime.DateTimeUnit.DAY),
                time = LocalTime(11, 0),
                status = BookingStatus.UPCOMING,
                sessionType = "HIIT",
                duration = 45
            ),
            // Cancelled bookings
            Booking(
                id = "6",
                trainerId = "5",
                trainerName = "Lisa Anderson",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/women/68.jpg",
                date = today.minus(DatePeriod(days = 2)),
                time = LocalTime(15, 0),
                status = BookingStatus.CANCELLED,
                sessionType = "Pilates",
                duration = 60
            ),
            Booking(
                id = "7",
                trainerId = "2",
                trainerName = "Mike Chen",
                trainerPhotoUrl = "https://randomuser.me/api/portraits/men/32.jpg",
                date = today.plus((DatePeriod(days = 1))),
                time = LocalTime(18, 0),
                status = BookingStatus.CANCELLED,
                sessionType = "Boxing",
                duration = 60
            )
        )
    }
}
