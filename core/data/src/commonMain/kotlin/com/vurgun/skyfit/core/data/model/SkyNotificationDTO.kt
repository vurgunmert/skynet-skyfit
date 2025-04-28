package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SkyFitNotification(
    val id: String,
    val title: String,
    val message: String,
    val category: NotificationCategory = NotificationCategory.ACTIVITY,
    val type: NotificationType = NotificationType.INFO,
    val senderId: String? = null,
    val recipientId: String,
    val timestamp: String,
    val isRead: Boolean = false,
    val isActionable: Boolean = true,
    val actionUrl: String? = null,
    val metadata: Map<String, String>? = null,
    val iconId: String? = null,
    val iconUrl: String? = null,
    val mediaUrls: List<String>? = null,
    val priority: NotificationPriority = NotificationPriority.MEDIUM,
    val tags: List<String>? = null,
    val fitnessEventId: String? = null, // Event associated with the notification
    val fitnessGoal: String? = null, // Related fitness goal
    val userId: String? = null
)


@Serializable
enum class NotificationCategory {
    COMMENT, LIKE, FOLLOW, MENTION, SYSTEM, ACTIVITY, ALERT, OTHER
}

@Serializable
enum class NotificationType {
    CRITICAL, WARNING, INFO, SUCCESS, ERROR, SYSTEM
}

@Serializable
enum class NotificationPriority {
    HIGH, MEDIUM, LOW
}