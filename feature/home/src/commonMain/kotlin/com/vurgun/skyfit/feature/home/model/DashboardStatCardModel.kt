package com.vurgun.skyfit.feature.home.model

import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData

data class DashboardStatCardModel(
    val primaryMetrics: List<StatisticCardUiData>,
    val chartData: List<Any> = emptyList(),
    val timeRange: TimeRange = TimeRange.H
) {
    enum class TimeRange {
        Y, M6, M3, M1, H
    }
}