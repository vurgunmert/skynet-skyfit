package com.vurgun.skyfit.core.data.v1.domain.facility.model

import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import kotlinx.datetime.LocalDate

data class FacilityMemberPackage(
    val memberPackageId: Int,
    val packageId: Int,
    val packageName: String,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val lessonCount: Int,
    val usedLessonCount: Int,
    val categories: List<LessonCategory>
)