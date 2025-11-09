package com.elixirgym.elixir.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.elixirgym.elixir.data.SampleBookingData
import com.elixirgym.elixir.domain.model.Booking
import com.elixirgym.elixir.domain.model.BookingStatus
import com.elixirgym.elixir.presentation.components.BottomToolbar
import kotlinx.datetime.*
import kotlin.time.ExperimentalTime

class CalendarScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val bookings = remember { SampleBookingData.getBookings() }

        var currentMonth by remember {
            mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault()))
        }

        var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My Schedule") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                    .padding(16.dp)
            ) {
                // Calendar Legend
                CalendarLegend()

                Spacer(modifier = Modifier.height(16.dp))

                // Month Navigation
                MonthNavigationBar(
                    currentMonth = currentMonth,
                    onPreviousMonth = {
                        currentMonth = currentMonth.minus(1, DateTimeUnit.MONTH)
                    },
                    onNextMonth = {
                        currentMonth = currentMonth.plus(1, DateTimeUnit.MONTH)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Calendar Grid
                CalendarGrid(
                    currentMonth = currentMonth,
                    bookings = bookings,
                    onDateClick = { date ->
                        val dateBookings = bookings.filter { it.date == date }
                        if (dateBookings.isEmpty()) {
                            // Navigate to trainer list for booking
                            navigator.push(TrainerListScreen())
                        } else {
                            // Show booking details
                            selectedDate = date
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selected Date Bookings
                selectedDate?.let { date ->
                    val dateBookings = bookings.filter { it.date == date }
                    if (dateBookings.isNotEmpty()) {
                        BookingsList(
                            date = date,
                            bookings = dateBookings,
                            onDismiss = { selectedDate = null },
                            onAddBooking = {
                                navigator.push(TrainerListScreen(preSelectedDate = date))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarLegend() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem(
                color = Color(0xFF4CAF50),
                label = "Current"
            )
            LegendItem(
                color = Color(0xFF2196F3),
                label = "Upcoming"
            )
            LegendItem(
                color = Color(0xFFF44336),
                label = "Cancelled"
            )
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MonthNavigationBar(
    currentMonth: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
        }

        Text(
            text = "${currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = onNextMonth) {
            Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
        }
    }
}

@Composable
fun CalendarGrid(
    currentMonth: LocalDate,
    bookings: List<Booking>,
    onDateClick: (LocalDate) -> Unit
) {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    // Get the first day of the month
    val firstDayOfMonth = LocalDate(currentMonth.year, currentMonth.month, 1)

    // Calculate the number of days in the month
    val daysInMonth = when (currentMonth.month) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
        Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if (isLeapYear(currentMonth.year)) 29 else 28
        else -> 31
    }

    val lastDayOfMonth = LocalDate(
        currentMonth.year,
        currentMonth.month,
        daysInMonth
    )

    // Calculate the day of week for the first day (0 = Sunday, 6 = Saturday)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal.let {
        if (it == 6) 0 else it + 1
    }

    // Create the calendar days list
    val calendarDays = mutableListOf<CalendarDay>()

    // Add empty days before the first day of month
    repeat(firstDayOfWeek) {
        calendarDays.add(CalendarDay.Empty)
    }

    // Add all days of the month
    for (day in 1..lastDayOfMonth.dayOfMonth) {
        val date = LocalDate(currentMonth.year, currentMonth.month, day)
        val dayBookings = bookings.filter { it.date == date }
        calendarDays.add(CalendarDay.Day(date, dayBookings))
    }

    Column {
        // Days of week header
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(daysOfWeek) { day ->
                Text(
                    text = day,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Calendar days grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(calendarDays) { calendarDay ->
                when (calendarDay) {
                    is CalendarDay.Empty -> {
                        Box(modifier = Modifier.size(48.dp))
                    }
                    is CalendarDay.Day -> {
                        CalendarDayCell(
                            date = calendarDay.date,
                            bookings = calendarDay.bookings,
                            onClick = { onDateClick(calendarDay.date) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun CalendarDayCell(
    date: LocalDate,
    bookings: List<Booking>,
    onClick: () -> Unit
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val isToday = date == today

    val backgroundColor = when {
        bookings.isEmpty() -> Color.Transparent
        bookings.any { it.status == BookingStatus.CURRENT } -> Color(0xFF4CAF50).copy(alpha = 0.2f)
        bookings.any { it.status == BookingStatus.UPCOMING } -> Color(0xFF2196F3).copy(alpha = 0.2f)
        bookings.all { it.status == BookingStatus.CANCELLED } -> Color(0xFFF44336).copy(alpha = 0.2f)
        else -> Color.Transparent
    }

    val borderColor = when {
        bookings.any { it.status == BookingStatus.CURRENT } -> Color(0xFF4CAF50)
        bookings.any { it.status == BookingStatus.UPCOMING } -> Color(0xFF2196F3)
        bookings.all { it.status == BookingStatus.CANCELLED } -> Color(0xFFF44336)
        else -> Color.LightGray
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .border(
                width = if (isToday) 2.dp else 1.dp,
                color = if (isToday) MaterialTheme.colorScheme.primary else borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                fontSize = 14.sp,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )

            if (bookings.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(borderColor)
                )
            }
        }
    }
}

@Composable
fun BookingsList(
    date: LocalDate,
    bookings: List<Booking>,
    onDismiss: () -> Unit,
    onAddBooking: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Bookings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.dayOfMonth}, ${date.year}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            bookings.forEach { booking ->
                BookingItem(booking)
                if (booking != bookings.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onAddBooking,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Add New Booking")
            }
        }
    }
}

@Composable
fun BookingItem(booking: Booking) {
    val statusColor = when (booking.status) {
        BookingStatus.CURRENT -> Color(0xFF4CAF50)
        BookingStatus.UPCOMING -> Color(0xFF2196F3)
        BookingStatus.CANCELLED -> Color(0xFFF44336)
    }

    val statusText = when (booking.status) {
        BookingStatus.CURRENT -> "Current"
        BookingStatus.UPCOMING -> "Upcoming"
        BookingStatus.CANCELLED -> "Cancelled"
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = booking.trainerName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = booking.sessionType,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${booking.time} • ${booking.duration} min",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = statusColor.copy(alpha = 0.2f)
        ) {
            Text(
                text = statusText,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                fontSize = 12.sp,
                color = statusColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

sealed class CalendarDay {
    object Empty : CalendarDay()
    data class Day(val date: LocalDate, val bookings: List<Booking>) : CalendarDay()
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

