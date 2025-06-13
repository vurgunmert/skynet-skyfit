package com.vurgun.skyfit.feature.connect.notification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.data.notification.NotificationCategory
import com.vurgun.skyfit.core.data.v1.data.notification.NotificationPriority
import com.vurgun.skyfit.core.data.v1.data.notification.NotificationType
import com.vurgun.skyfit.core.data.v1.data.notification.SkyFitNotificationDTO
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

enum class NotificationTab {
    ALL, ACTIVITY, COMMUNITY
}

sealed class NotificationsUiState {
    object Loading : NotificationsUiState()

    data class Content(
        val all: List<SkyFitNotificationDTO> = emptyList(),
        val activity: List<SkyFitNotificationDTO> = emptyList(),
        val community: List<SkyFitNotificationDTO> = emptyList(),
        val tabTitles: List<String> = emptyList(),
        val activeTab: NotificationTab = NotificationTab.ALL
    ) : NotificationsUiState()

    data class Error(val message: String) : NotificationsUiState()
}

sealed class NotificationsAction {
    data object LoadData : NotificationsAction()
    data class Delete(val notification: SkyFitNotificationDTO) : NotificationsAction()
    data object DeleteAll : NotificationsAction()
    data object ClickBack : NotificationsAction()
    data object ClickSettings : NotificationsAction()
    data class ChangeTab(val tab: NotificationTab) : NotificationsAction()
}

sealed class NotificationsEffect {
    object NavigateBack : NotificationsEffect()
    object NavigateSettings : NotificationsEffect()
}

class NotificationsViewModel : ScreenModel {

    private val _uiState = UiStateDelegate<NotificationsUiState>(NotificationsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<NotificationsEffect>()
    val effect = _effect as SharedFlow<NotificationsEffect>

    private val allNotifications = mutableListOf<SkyFitNotificationDTO>()

    fun onAction(action: NotificationsAction) {
        when (action) {
            NotificationsAction.LoadData -> loadData()

            is NotificationsAction.Delete -> {
                allNotifications.remove(action.notification)
                updateUiState()
            }

            NotificationsAction.DeleteAll -> {
                allNotifications.clear()
                updateUiState()
            }

            NotificationsAction.ClickBack -> {
                _effect.emitIn(screenModelScope, NotificationsEffect.NavigateBack)
            }

            is NotificationsAction.ChangeTab -> {
                val currentState = uiState.value
                if (currentState is NotificationsUiState.Content) {
                    _uiState.update(currentState.copy(activeTab = action.tab))
                }
            }

            NotificationsAction.ClickSettings -> {
                _effect.emitIn(screenModelScope, NotificationsEffect.NavigateSettings)
            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            _uiState.update(NotificationsUiState.Loading)

            // Simulated data — replace with real fetch
            allNotifications.clear()
            allNotifications.addAll(
                listOf(
                    SkyFitNotificationDTO(
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
                    SkyFitNotificationDTO(
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
                    SkyFitNotificationDTO(
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
                    SkyFitNotificationDTO(
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
            )

            updateUiState()
        }
    }

    private fun updateUiState() {
        val all = allNotifications.toList()

        val activity = all.filter { it.category == NotificationCategory.ACTIVITY }
        val community = all.filter {
            it.category == NotificationCategory.COMMENT ||
                    it.category == NotificationCategory.LIKE ||
                    it.category == NotificationCategory.FOLLOW
        }

        val titles = listOf(
            "Tümü (${all.size})",
            "Aktiviteler (${activity.size})",
            "Topluluk (${community.size})"
        )

        _uiState.update(
            NotificationsUiState.Content(
                all = all,
                activity = activity,
                community = community,
                tabTitles = titles
            )
        )
    }
}
