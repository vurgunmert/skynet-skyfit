package com.vurgun.skyfit.feature.connect.notification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.connect.data.model.NotificationCategory
import com.vurgun.skyfit.core.data.connect.data.model.NotificationPriority
import com.vurgun.skyfit.core.data.connect.data.model.NotificationType
import com.vurgun.skyfit.core.data.connect.data.model.SkyFitNotification
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

enum class NotificationTab {
    ALL, ACTIVITY, COMMUNITY
}

sealed class UserNotificationsUiState {
    object Loading : UserNotificationsUiState()

    data class Content(
        val all: List<SkyFitNotification> = emptyList(),
        val activity: List<SkyFitNotification> = emptyList(),
        val community: List<SkyFitNotification> = emptyList(),
        val tabTitles: List<String> = emptyList(),
        val activeTab: NotificationTab = NotificationTab.ALL
    ) : UserNotificationsUiState()

    data class Error(val message: String) : UserNotificationsUiState()
}

sealed class UserNotificationsAction {
    data object LoadData : UserNotificationsAction()
    data class Delete(val notification: SkyFitNotification) : UserNotificationsAction()
    data object DeleteAll : UserNotificationsAction()
    data object ClickBack : UserNotificationsAction()
    data object ClickSettings : UserNotificationsAction()
    data class ChangeTab(val tab: NotificationTab) : UserNotificationsAction()
}

sealed class UserNotificationsEffect {
    object NavigateBack : UserNotificationsEffect()
    object NavigateSettings : UserNotificationsEffect()
}

class UserNotificationsViewModel : ScreenModel {

    private val _uiState = UiStateDelegate<UserNotificationsUiState>(UserNotificationsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserNotificationsEffect>()
    val effect = _effect as SharedFlow<UserNotificationsEffect>

    private val allNotifications = mutableListOf<SkyFitNotification>()

    fun onAction(action: UserNotificationsAction) {
        when (action) {
            UserNotificationsAction.LoadData -> loadData()

            is UserNotificationsAction.Delete -> {
                allNotifications.remove(action.notification)
                updateUiState()
            }

            UserNotificationsAction.DeleteAll -> {
                allNotifications.clear()
                updateUiState()
            }

            UserNotificationsAction.ClickBack -> {
                _effect.emitIn(screenModelScope, UserNotificationsEffect.NavigateBack)
            }

            is UserNotificationsAction.ChangeTab -> {
                val currentState = uiState.value
                if (currentState is UserNotificationsUiState.Content) {
                    _uiState.update(currentState.copy(activeTab = action.tab))
                }
            }

            UserNotificationsAction.ClickSettings -> {
                _effect.emitIn(screenModelScope, UserNotificationsEffect.NavigateSettings)
            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            _uiState.update(UserNotificationsUiState.Loading)

            // Simulated data — replace with real fetch
            allNotifications.clear()
            allNotifications.addAll(
                listOf(
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
            UserNotificationsUiState.Content(
                all = all,
                activity = activity,
                community = community,
                tabTitles = titles
            )
        )
    }
}
