package com.vurgun.skyfit.data.network.utils

fun String.toSlug(): String {
    return this.lowercase()
        .trim() // Remove leading/trailing spaces
        .replace(Regex("[^a-z0-9\\s-]"), "") // Remove special characters
        .replace(Regex("\\s+"), "-") // Replace spaces with hyphens
        .replace(Regex("-+"), "-") // Remove multiple consecutive hyphens
}