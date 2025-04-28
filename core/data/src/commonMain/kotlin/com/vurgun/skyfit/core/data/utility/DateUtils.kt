package com.vurgun.skyfit.core.data.utility

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

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

fun LocalDateTime.formatToServerTime(): String {
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')
    val second = this.second.toString().padStart(2, '0')
    return "$hour:$minute:$second"
}

fun String.parseServerToDateOnly(): LocalDate {
    return LocalDate.parse(this.substringBefore("T"))
}
fun String.parseServerToHHMMTime(): LocalTime {
    return LocalTime.parse(this.substring(0, 5))
}