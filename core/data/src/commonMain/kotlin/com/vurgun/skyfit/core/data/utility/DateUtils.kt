package com.vurgun.skyfit.core.data.utility

import kotlinx.datetime.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

// Helpers for LocalDate
fun generateDaysInMonth(month: LocalDate): List<LocalDate> {
    val firstDay = LocalDate(month.year, month.monthNumber, 1)
    val totalDays = getDaysInMonth(firstDay)
    val startDayOffset = (firstDay.dayOfWeek.isoDayNumber - 1) % 7

    val previousMonth = previousMonth(month)
    val daysInPreviousMonth = getDaysInMonth(previousMonth)

    val leadingDays = (daysInPreviousMonth - startDayOffset + 1..daysInPreviousMonth).map {
        LocalDate(previousMonth.year, previousMonth.monthNumber, it)
    }

    val currentMonthDays = (1..totalDays).map {
        LocalDate(month.year, month.monthNumber, it)
    }

    val maxDays = 42
    val nextMonth = nextMonth(month)
    val trailingDays = (1..maxDays - leadingDays.size - currentMonthDays.size).map {
        LocalDate(nextMonth.year, nextMonth.monthNumber, it)
    }

    return leadingDays + currentMonthDays + trailingDays
}

fun updateRangeSelection(day: LocalDate, currentRange: Pair<LocalDate, LocalDate>?): Pair<LocalDate, LocalDate> {
    return if (currentRange == null || currentRange.second != null) {
        // Start a new range
        day to day
    } else {
        // Complete the range
        val (start, _) = currentRange
        if (day < start) day to start else start to day
    }
}

fun getDaysInMonth(date: LocalDate): Int {
    val nextMonth = nextMonth(date)
    return nextMonth.minus(1, DateTimeUnit.DAY).dayOfMonth
}

fun previousMonth(date: LocalDate): LocalDate {
    return if (date.monthNumber == 1) LocalDate(date.year - 1, 12, 1)
    else LocalDate(date.year, date.monthNumber - 1, 1)
}

fun nextMonth(date: LocalDate): LocalDate {
    return if (date.monthNumber == 12) LocalDate(date.year + 1, 1, 1)
    else LocalDate(date.year, date.monthNumber + 1, 1)
}

fun LocalDate.isBefore(other: LocalDate): Boolean = this < other
fun LocalDate.isAfter(other: LocalDate): Boolean = this > other
fun LocalDate.Companion.now(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

fun LocalDateTime.Companion.now(): LocalDateTime =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.plusDuration(
    duration: Duration,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    val instant = this.toInstant(timeZone)
    val newInstant = instant + duration
    return newInstant.toLocalDateTime(timeZone)
}

fun LocalDateTime.roundUpToNextSlot(timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime {
    return if (this.minute <= 30) {
        LocalDateTime(
            year = this.year,
            month = this.month,
            dayOfMonth = this.dayOfMonth,
            hour = this.hour,
            minute = 30,
            second = 0,
            nanosecond = 0
        )
    } else {
        val nextHour = this.toInstant(timeZone)
            .plus(1.hours)
            .toLocalDateTime(timeZone)
        LocalDateTime(
            year = nextHour.year,
            month = nextHour.month,
            dayOfMonth = nextHour.dayOfMonth,
            hour = nextHour.hour,
            minute = 0,
            second = 0,
            nanosecond = 0
        )
    }
}

fun LocalDateTime.isBeforeNow(): Boolean =
    this < LocalDateTime.now()

fun LocalDateTime.isAfterNow(): Boolean =
    this > LocalDateTime.now()

fun LocalDate.withDayOfMonth(day: Int): LocalDate {
    return this.minus(dayOfMonth - day, DateTimeUnit.DAY)
}

fun LocalDate.lengthOfMonth(): Int {
    val nextMonth = this.plus(1, DateTimeUnit.MONTH)
    return nextMonth.withDayOfMonth(1).minus(1, DateTimeUnit.DAY).dayOfMonth
}

fun getTurkishMonthName(month: Int): String {
    return when (month) {
        1 -> "Ocak"
        2 -> "Şubat"
        3 -> "Mart"
        4 -> "Nisan"
        5 -> "Mayıs"
        6 -> "Haziran"
        7 -> "Temmuz"
        8 -> "Ağustos"
        9 -> "Eylül"
        10 -> "Ekim"
        11 -> "Kasım"
        12 -> "Aralık"
        else -> ""
    }
}

fun getTurkishDayAbbreviation(date: LocalDate): String {
    return when (date.dayOfWeek.isoDayNumber) {
        1 -> "Pzt"
        2 -> "Sal"
        3 -> "Çar"
        4 -> "Per"
        5 -> "Cum"
        6 -> "Cmrt"
        7 -> "Paz"
        else -> ""
    }
}

fun getDaysInMonth(month: Int, year: Int): Int {
    return when (month) {
        4, 6, 9, 11 -> 30 // April, June, September, November
        2 -> if (isLeapYear(year)) 29 else 28 // February
        else -> 31 // Rest have 31 days
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun getStartOfWeek(date: LocalDate): LocalDate {
    val offset = (date.dayOfWeek.isoDayNumber - 1) % 7
    return date.minus(offset, DateTimeUnit.DAY)
}

fun LocalDate.previousWeek(): LocalDate = this.minus(7, DateTimeUnit.DAY)
fun LocalDate.nextWeek(): LocalDate = this.plus(7, DateTimeUnit.DAY)

fun LocalDateTime.formatToServerDate(): String {
    val year = this.date.year.toString().padStart(4, '0')
    val month = this.date.monthNumber.toString().padStart(2, '0')
    val day = this.date.dayOfMonth.toString().padStart(2, '0')
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')
    val second = this.second.toString().padStart(2, '0')
    return "$year-$month-$day $hour:$minute:$second"
}

fun LocalDate.formatToServerDate(): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val day = this.dayOfMonth.toString().padStart(2, '0')
    return "$year-$month-$day 00:00:00"
}

fun LocalDate.toServerFormatStartOfDate(): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val day = this.dayOfMonth.toString().padStart(2, '0')
    return "$year-$month-${day}T00:00:00.000Z"
}

fun LocalDate.toServerFormatEndOfDate(): String {
    val year = this.year.toString().padStart(4, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val day = this.dayOfMonth.toString().padStart(2, '0')
    return "$year-$month-${day}T23:59:59.000Z"
}

fun LocalDateTime.formatToHHMMTime(): String {
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')
    return "$hour:$minute"
}


fun LocalDateTime.formatToServerTime(): String {
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')
    val second = this.second.toString().padStart(2, '0')
    return "$hour:$minute:$second"
}

fun LocalDate.formatToSlashedDate(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val year = this.year.toString()
    return "$day/$month/$year"
}

fun String.parseServerToDateOnly(): LocalDate {
    return LocalDate.parse(this.substringBefore("T"))
}

fun String.parseServerToLocalDateTime(
    zone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Instant.parse(this).toLocalDateTime(zone)
}


fun String.parseServerToHHMMTime(): LocalTime {
    return LocalTime.parse(this.substring(0, 5))
}

fun String.toMinutesOfDay(): Int {
    val (hour, minute) = this.split(":").map { it.toIntOrNull() ?: 0 }
    return hour * 60 + minute
}

fun LocalDate.toTurkishLongDate(): String {
    val monthName = turkishMonths[monthNumber] ?: ""
    val dayName = turkishDays[dayOfWeek.isoDayNumber] ?: ""
    return "$dayOfMonth $monthName $dayName"
}

private val turkishMonths = mapOf(
    1 to "Ocak", 2 to "Şubat", 3 to "Mart", 4 to "Nisan", 5 to "Mayıs", 6 to "Haziran",
    7 to "Temmuz", 8 to "Ağustos", 9 to "Eylül", 10 to "Ekim", 11 to "Kasım", 12 to "Aralık"
)

private val turkishDays = mapOf(
    1 to "Pazartesi", 2 to "Salı", 3 to "Çarşamba", 4 to "Perşembe",
    5 to "Cuma", 6 to "Cumartesi", 7 to "Pazar"
)

fun durationBetween(
    start: LocalDateTime,
    end: LocalDateTime,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Duration {
    val startInstant = start.toInstant(timeZone)
    val endInstant = end.toInstant(timeZone)
    return endInstant - startInstant
}

fun daysBetween(start: LocalDate, end: LocalDate): Int {
    return end.toEpochDays() - start.toEpochDays()
}

data class DurationComponents(val hours: Int, val minutes: Int)

fun Duration.toHourMinute(): DurationComponents {
    val totalMinutes = this.inWholeMinutes
    val hours = (totalMinutes / 60).toInt()
    val minutes = (totalMinutes % 60).toInt()
    return DurationComponents(hours, minutes)
}

fun LocalDateTime.humanizeAgo(
    now: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
): String {
    val duration = durationBetween(this, now)

    val minutes = duration.inWholeMinutes
    val hours = duration.inWholeHours
    val days = duration.inWholeDays

    return when {
        minutes < 60 -> "$minutes dk önce"
        hours < 24 -> "$hours saat önce"
        days in 1..9 -> "$days gün önce"
        else -> {
            val day = this.dayOfMonth.toString().padStart(2, '0')
            val month = this.monthNumber.toString().padStart(2, '0')
            val year = this.year.toString()
            "$day/$month/$year"
        }
    }
}