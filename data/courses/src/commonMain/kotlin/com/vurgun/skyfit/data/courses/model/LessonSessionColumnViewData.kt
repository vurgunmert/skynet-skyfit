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
    val iconId: String,
    val title: String,
    val date: String? = null,
    val hours: String? = null,
    val duration: String? = null,
    val trainer: String? = null,
    val category: String? = null,
    val location: String? = null,
    val note: String? = null,
    val enrolledCount: Int? = null,
    val maxCapacity: Int? = null,
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val sessionId: String = Uuid.random().toString(),
) {
    fun isBooked(userBookedSessionIds: List<String>) = sessionId in userBookedSessionIds
}
