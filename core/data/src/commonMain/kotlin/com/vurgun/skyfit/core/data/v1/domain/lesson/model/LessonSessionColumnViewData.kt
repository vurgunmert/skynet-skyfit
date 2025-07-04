package com.vurgun.skyfit.core.data.v1.domain.lesson.model;

data class LessonSessionColumnViewData(
    val iconId: Int,
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
    val statusName: String,
    val isActive: Boolean = true,
    val selected: Boolean = false,
)