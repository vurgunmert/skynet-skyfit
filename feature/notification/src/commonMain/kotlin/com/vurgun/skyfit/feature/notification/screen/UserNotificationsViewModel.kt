package com.vurgun.skyfit.feature.notification.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.model.NotificationCategory
import com.vurgun.skyfit.core.data.model.NotificationPriority
import com.vurgun.skyfit.core.data.model.NotificationType
import com.vurgun.skyfit.core.data.model.SkyFitNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserNotificationsViewModel : ScreenModel {

    private lateinit var userId: String

    private val _notifications = MutableStateFlow<List<SkyFitNotification>>(emptyList())

    val allNotifications: StateFlow<List<SkyFitNotification>> = _notifications.stateIn(
        scope = screenModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = emptyList()
    )

    val activityNotifications: StateFlow<List<SkyFitNotification>> = _notifications.map { appointments ->
        appointments.filter { it.category == NotificationCategory.ACTIVITY }
    }.stateIn(
        scope = screenModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = emptyList()
    )

    val communityNotifications: StateFlow<List<SkyFitNotification>> = _notifications.map { appointments ->
        appointments.filter { it.category == NotificationCategory.COMMENT || it.category == NotificationCategory.LIKE || it.category == NotificationCategory.FOLLOW }
    }.stateIn(
        scope = screenModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = emptyList()
    )

    val tabTitles: StateFlow<List<String>> = _notifications.map {
        listOf(
            "Tümü (${allNotifications.value.size})",
            "Aktiviteler (${activityNotifications.value.size})",
            "Topluluk (${communityNotifications.value.size})"
        )
    }.stateIn(
        scope = screenModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun loadData() {
//        userId = Firebase.auth.currentUser?.uid ?: return

        screenModelScope.launch {
            _notifications.value = listOf(
                SkyFitNotification(
                    id = "1",
                    title = "New Follower",
                    message = "JohnDoe started following you!",
                    category = NotificationCategory.FOLLOW,
                    type = NotificationType.SUCCESS,
                    senderId = "user_1234",
                    recipientId = "user_5678",
                    timestamp = "2025-01-28T14:15:00Z",
                    isRead = false,
                    isActionable = true,
                    actionUrl = "https://skyfit.com/followers",
                    priority = NotificationPriority.MEDIUM,
                    tags = listOf("social", "followers")
                ),

                SkyFitNotification(
                    id = "2",
                    title = "Workout Reminder",
                    message = "Don’t forget your scheduled workout at 6 PM today!",
                    category = NotificationCategory.ACTIVITY,
                    type = NotificationType.INFO,
                    recipientId = "user_5678",
                    timestamp = "2025-01-28T08:00:00Z",
                    isRead = false,
                    isActionable = false,
                    priority = NotificationPriority.HIGH,
                    tags = listOf("reminder", "fitness"),
                    fitnessEventId = "event_101"
                ),

                SkyFitNotification(
                    id = "3",
                    title = "System Alert",
                    message = "Your subscription is about to expire in 3 days. Renew now to keep your access!",
                    category = NotificationCategory.SYSTEM,
                    type = NotificationType.WARNING,
                    recipientId = "user_5678",
                    timestamp = "2025-01-27T10:00:00Z",
                    isRead = false,
                    isActionable = true,
                    actionUrl = "https://skyfit.com/subscription",
                    priority = NotificationPriority.HIGH,
                    tags = listOf("subscription", "renewal"),
                    metadata = mapOf("expiryDate" to "2025-01-30")
                ),

                SkyFitNotification(
                    id = "4",
                    title = "Achievement Unlocked!",
                    message = "Congratulations! You’ve completed your weekly fitness goal.",
                    category = NotificationCategory.ACTIVITY,
                    type = NotificationType.SUCCESS,
                    recipientId = "user_5678",
                    timestamp = "2025-01-28T12:30:00Z",
                    isRead = true,
                    isActionable = false,
                    fitnessGoal = "Complete 5 workouts in a week",
                    priority = NotificationPriority.LOW,
                    tags = listOf("achievement", "fitness")
                )
            )
        }

    }

    fun deleteNotification(notification: SkyFitNotification) {
        screenModelScope.launch {
            _notifications.value = _notifications.value.filter { it != notification }
        }
    }

    fun deleteAllNotifications() {
        screenModelScope.launch {
            _notifications.value = emptyList()
        }
    }
}