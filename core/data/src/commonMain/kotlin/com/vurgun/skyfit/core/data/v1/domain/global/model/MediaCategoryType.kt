package com.vurgun.skyfit.core.data.v1.domain.global.model

enum class MediaCategoryType(val id: String) {
    EXERCISE("exercise"),
    MEAL("meal"),
    BODY("body"),
    PROGRESS("progress"),
    OTHER("other");

    companion object {
        fun from(id: String): MediaCategoryType =
            values().firstOrNull { it.id == id } ?: OTHER
    }
}
