package com.vurgun.skyfit.core.data.v1.data.global.model

data class ChallengeDTO(
    val id: String,
    val title: String,
    val description: String,
    val startDate: String,  // ISO Date
    val endDate: String,
    val category: ChallengeCategoryDTO,
    val type: ChallengeTypeDTO, // e.g., step-count, calorie-burn
    val unit: UnitDescriptionDTO, // e.g., km, steps
    val goalValue: Float, // target
    val participantCount: Int,
    val creatorId: String,
    val imageUrlPath: String?,
    val isPublic: Boolean
)
