package com.vurgun.skyfit.core.ui.utils

fun formatPhoneNumber(phoneNumber: String): String {
    val cleaned = phoneNumber.filter { it.isDigit() } // Remove non-numeric characters
    return if (cleaned.length == 10) {
        "+90 ${cleaned.substring(0, 3)} ${cleaned.substring(3, 6)} ${cleaned.substring(6, 8)} ${cleaned.substring(8, 10)}"
    } else if (cleaned.length == 11 && cleaned.startsWith("90")) {
        "+${cleaned.substring(0, 2)} ${cleaned.substring(2, 5)} ${cleaned.substring(5, 8)} ${cleaned.substring(8, 10)} ${cleaned.substring(10, 11)}"
    } else {
        phoneNumber // Return as is if format is unexpected
    }
}