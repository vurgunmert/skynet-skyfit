package com.vurgun.skyfit.feature.home.model

data class HomeAiAssistantState(
    val isMini: Boolean = false,
    val postureEnabled: Boolean = false,
    val nutritionEnabled: Boolean = false,
    val chatRecommendations: List<String> = emptyList(),
)