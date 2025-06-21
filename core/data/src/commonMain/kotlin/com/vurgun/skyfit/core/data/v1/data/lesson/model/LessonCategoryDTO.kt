package com.vurgun.skyfit.core.data.v1.data.lesson.model

import kotlinx.serialization.Serializable

@Serializable
data class LessonCategoryDTO(
    val categoryId: Int,
    val categoryName: String,
)

@Serializable
data class GetLessonCategoriesRequestDTO(
    val gymId: Int,
)

@Serializable
data class AddLessonCategoryRequestDTO(
    val categoryName: String,
    val gymId: Int,
)

@Serializable
data class DeleteLessonCategoryRequestDTO(
    val categoryId: Int,
    val gymId: Int,
)

@Serializable
data class UpdateLessonCategoryRequestDTO(
    val categoryId: Int,
    val categoryName: String,
    val gymId: Int,
)