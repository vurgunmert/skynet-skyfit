package com.vurgun.skyfit.core.data.v1.data.facility.model

import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonCategoryDTO
import kotlinx.serialization.Serializable

@Serializable
data class CreateFacilityLessonPackageRequestDTO(
    val gymId: Int,
    val title: String,
    val categories: List<Int>,
    val description: String,
    val lessonCount: Int,
    val duration: Int,
    val price: Float,
)

@Serializable
data class UpdateFacilityLessonPackageRequestDTO(
    val packageId: Int,
    val title: String,
    val categories: List<Int>,
    val description: String,
    val lessonCount: Int,
    val duration: Int,
    val price: Float,
)

@Serializable
data class GetFacilityLessonPackagesRequestDTO(
    val gymId: Int,
)

@Serializable
data class DeleteFacilityLessonPackagesRequestDTO(
    val packageId: Int,
)

@Serializable
data class FacilityLessonPackageDTO(
    val packageId: Int,
    val title: String,
    val description: String,
    val lessonCount: Int,
    val duration: Int,
    val price: Float,
    val packageMemberCount: Int,
    val packageContents: List<String>,
)

@Serializable
data class FacilityMemberPackageDTO(
    val memberPackageId: Int,
    val packageId: Int,
    val packageName: String,
    val startDate: String,
    val endDate: String?,
    val lessonCount: Int,
    val usedLessonCount: Int? = null,
    val categories: List<LessonCategoryDTO>? = null
)
