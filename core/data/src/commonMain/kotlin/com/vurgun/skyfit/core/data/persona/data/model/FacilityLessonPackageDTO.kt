package com.vurgun.skyfit.core.data.persona.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateFacilityLessonPackageRequestDTO(
    val gymId: Int,
    val title: String,
    val contents: List<Int>,
    val description: String,
    val lessonCount: Int,
    val duration: Int,
    val price: Float,
)

@Serializable
data class UpdateFacilityLessonPackageRequestDTO(
    val packageId: Int,
    val title: String,
    val contents: List<Int>,
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
