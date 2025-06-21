package com.vurgun.skyfit.core.data.v1.statistics.back.model

import kotlinx.serialization.Serializable

@Serializable
data class StatisticsDTO(
    val spectator: SpectatorStatisticsDTO? = null,
    val user: UserStatisticsDTO? = null,
    val trainer: TrainerStatisticsDTO? = null,
    val facility: FacilityStatisticsDTO? = null
)

@Serializable
data class ActivityStatisticsDTO(
    val activeTotalDistance: Double,   // meters
    val activeTotalCalory: Double,     // kcal
    val activeDurationSec: Long        // seconds
)

@Serializable
data class SpectatorStatisticsDTO(
    val visitCount: Int
)

@Serializable
data class UserStatisticsDTO(
    val goalStatistics: GoalStatisticsDTO,
    val activityStatistics: ActivityStatisticsDTO
)

@Serializable
data class GoalStatisticsDTO(
    val totalGoalCount: Int,
    val completedGoalCount: Int
)

@Serializable
data class TrainerStatisticsDTO(
    val followerCount: Int,
    val activeLessonTypeCount: Int,
    val activeLessonCount: Int,
    val inboundEarning: Double? = null // in app currency
)

@Serializable
data class FacilityStatisticsDTO(
    val followerCount: Int,
    val memberCount: Int,
    val trainerCount: Int,
    val activeLessonTypeCount: Int,
    val activeLessonCount: Int,
    val inboundEarning: Double? = null
)
