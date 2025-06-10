package com.vurgun.skyfit.core.data.v1.domain.global.model

import kotlinx.datetime.Instant

data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val startDate: Instant,
    val endDate: Instant,
    val category: ChallengeCategory,
    val type: ChallengeType,
    val unit: UnitDescription,
    val goalValue: Float,
    val participantCount: Int,
    val creatorId: String,
    val imageUrl: String?,
    val isPublic: Boolean
)

data class ChallengeCategory(
    val id: Int,
    val nameEn: String,
    val nameTr: String
)

data class ChallengeType(
    val id: Int,
    val nameEn: String,
    val nameTr: String
)
