package com.vurgun.skyfit.core.data.v1.statistics.mapper

import com.vurgun.skyfit.core.data.v1.statistics.back.model.*
import com.vurgun.skyfit.core.data.v1.statistics.front.*

internal object StatisticDataMapper {

    fun StatisticsDTO.toDomain(): Statistics = Statistics(
        user = user?.toDomain(),
        facility = facility?.toDomain()
    )

    fun ActivityStatisticsDTO.toDomain() = ActivityStatistics(
        activeTotalDistance = activeTotalDistance,
        activeTotalCalory = activeTotalCalory,
        activeDurationSec = activeDurationSec
    )

    fun SpectatorStatisticsDTO.toDomain() = SpectatorStatistics(
        visitCount = visitCount
    )

    fun GoalStatisticsDTO.toDomain() = GoalStatistics(
        totalGoalCount = totalGoalCount,
        completedGoalCount = completedGoalCount
    )

    fun UserStatisticsDTO.toDomain() = UserStatistics(
        activity = activityStatistics.toDomain(),
        goal = goalStatistics.toDomain()
    )

    fun TrainerStatisticsDTO.toDomain() = TrainerStatistics(
        followerCount = followerCount,
        activeLessonTypeCount = activeLessonTypeCount,
        activeLessonCount = activeLessonCount,
        inboundEarning = inboundEarning
    )

    fun FacilityStatisticsDTO.toDomain() = FacilityStatistics(
        followerCount = followerCount,
        memberCount = memberCount,
        trainerCount = trainerCount,
        activeLessonTypeCount = activeLessonTypeCount,
        activeLessonCount = activeLessonCount,
        inboundEarning = inboundEarning
    )

}
