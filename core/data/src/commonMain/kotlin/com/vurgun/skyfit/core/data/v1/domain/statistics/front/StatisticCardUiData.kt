package com.vurgun.skyfit.core.data.v1.domain.statistics.front

data class StatisticCardUiData(
    val title: String,
    val value: String,
    val changePercent: Int? = null,
    val changeDirection: ChangeDirection? = null
) {
    enum class ChangeDirection {
        UP, DOWN, NEUTRAL
    }
}