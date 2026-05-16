package com.mohamedfaridelsherbini.vora.notes

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

internal fun Long.toDurationLabel(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "${minutes}:${seconds.toString().padStart(2, '0')}"
}

internal fun Long.toMinutesLabel(): String {
    val totalMinutes = (this / 60_000).coerceAtLeast(1)
    return "$totalMinutes min"
}

internal fun Long.toCreatedAtLabel(now: Long = currentTimeMillis()): String {
    val zone = TimeZone.currentSystemDefault()
    val created = kotlin.time.Instant.fromEpochMilliseconds(this).toLocalDateTime(zone)
    val current = kotlin.time.Instant.fromEpochMilliseconds(now).toLocalDateTime(zone)
    val createdDate = created.date
    val currentDate = current.date
    val prefix = when {
        createdDate == currentDate -> "Today"
        createdDate == currentDate.minus(DatePeriod(days = 1)) -> "Yesterday"
        else -> createdDate.dayLabel()
    }
    val time = "${created.hour}:${created.minute.toString().padStart(2, '0')}"
    return "$prefix, $time"
}

internal fun String.toRenamedTitle(): String = when {
    endsWith(" (Renamed)") -> this
    else -> "$this (Renamed)"
}

private fun LocalDate.dayLabel(): String = when (dayOfWeek) {
    DayOfWeek.MONDAY -> "Mon"
    DayOfWeek.TUESDAY -> "Tue"
    DayOfWeek.WEDNESDAY -> "Wed"
    DayOfWeek.THURSDAY -> "Thu"
    DayOfWeek.FRIDAY -> "Fri"
    DayOfWeek.SATURDAY -> "Sat"
    DayOfWeek.SUNDAY -> "Sun"
}
