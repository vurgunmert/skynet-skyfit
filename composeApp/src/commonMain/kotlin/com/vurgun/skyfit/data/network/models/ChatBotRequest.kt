package com.vurgun.skyfit.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatbotRequest(val question: String)

@Serializable
data class ChatbotResponse(val answer: String)