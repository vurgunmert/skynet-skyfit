@file:OptIn(ExperimentalUuidApi::class)
package com.vurgun.skyfit.data.courses.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class LessonSessionColumnViewData(
    val iconId: String,
    val title: String,
    val items: List<LessonSessionItemViewData>
)

data class LessonSessionItemViewData(
    val lessonId: Int,
    val iconId: Int,
    val title: String,
    val date: String? = null,
    val hours: String? = null,
    val duration: String? = null,
    val trainer: String? = null,
    val category: String? = null,
    val facility: String? = null,
    val location: String? = null,
    val note: String? = null,
    val enrolledCount: String? = null,
    val capacityRatio: String? = null,
    val isActive: Boolean = true,
    val selected: Boolean = false,
) {
    fun isBooked(userBookedSessionIds: List<String>) = false //TODO: Remove
}
