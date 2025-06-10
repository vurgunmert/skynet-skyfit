package com.vurgun.skyfit.core.data.v1.statistics.front

data class Statistics(
    val spectator: SpectatorStatistics? = null,
    val user: UserStatistics? = null,
    val trainer: TrainerStatistics? = null,
    val facility: FacilityStatistics? = null
)

data class SpectatorStatistics(
    val visitCount: Int
)

data class UserStatistics(
    val activity: ActivityStatistics? = null,
    val goal: GoalStatistics? = null,
)

data class ActivityStatistics(
    val activeTotalDistance: Double,   // meters
    val activeTotalCalory: Double,     // kcal
    val activeDurationSec: Long        // seconds
)

data class GoalStatistics(
    val totalGoalCount: Int,
    val completedGoalCount: Int
)

data class TrainerStatistics(
    val followerCount: Int,
    val activeLessonTypeCount: Int,
    val activeLessonCount: Int,
    val inboundEarning: Double? = null
)

data class FacilityStatistics(
    val followerCount: Int,
    val memberCount: Int,
    val trainerCount: Int,
    val activeLessonTypeCount: Int,
    val activeLessonCount: Int,
    val inboundEarning: Double? = null
)
