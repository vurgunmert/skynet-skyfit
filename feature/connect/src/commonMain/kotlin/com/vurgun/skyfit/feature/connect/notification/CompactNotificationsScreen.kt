package com.vurgun.skyfit.feature.connect.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.data.notification.SkyFitNotificationDTO
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.connect.component.NotificationItemSwipeDismissBackground
import com.vurgun.skyfit.feature.connect.component.SkyFitNotificationItem
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class CompactNotificationsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<NotificationsViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                NotificationsEffect.NavigateBack -> {
                    navigator.pop()
                }
                NotificationsEffect.NavigateSettings -> {
                    navigator.push(SharedScreen.Settings)
                }
            }
        }

        UserNotificationsComponent.Screen(viewModel)
    }
}


class ExpandedNotificationsScreen(
    private val onDismiss: () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<NotificationsViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                NotificationsEffect.NavigateBack -> onDismiss()
                NotificationsEffect.NavigateSettings -> {
                    navigator.push(SharedScreen.Settings)
                }
            }
        }

        UserNotificationsComponent.Screen(viewModel)
    }
}

private object UserNotificationsComponent {
    @Composable
    fun Screen(viewModel: NotificationsViewModel) {
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onAction(NotificationsAction.LoadData)
        }

        when (val state = uiState) {
            is NotificationsUiState.Loading -> {
                FullScreenLoaderContent()
            }

            is NotificationsUiState.Content -> {
                val content = state

                SkyFitMobileScaffold(
                    topBar = {
                        Column {
                            CompactTopBar(
                                title = stringResource(Res.string.notifications_label),
                                onClickBack = { viewModel.onAction(NotificationsAction.ClickBack) })

                            TextNumberBadgeTabBarComponent(
                                tabs = content.tabTitles,
                                selectedIndex = content.activeTab.ordinal,
                                onTabSelected = { index ->
                                    val tab = NotificationTab.entries[index]
                                    viewModel.onAction(NotificationsAction.ChangeTab(tab))
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if (content.all.isEmpty()) {
                            SettingsAction {
                                viewModel.onAction(NotificationsAction.ClickSettings)
                            }
                        } else {
                            DeleteAllAction {
                                viewModel.onAction(NotificationsAction.DeleteAll)
                            }
                        }
                    }
                ) {
                    UserNotificationsContent(
                        content = content,
                        onAction = viewModel::onAction
                    )
                }
            }

            is NotificationsUiState.Error -> {
                // TODO: handle error
            }
        }
    }

    @Composable
    private fun UserNotificationsContent(
        content: NotificationsUiState.Content,
        onAction: (NotificationsAction) -> Unit,
    ) {
        val list = when (content.activeTab) {
            NotificationTab.ALL -> content.all
            NotificationTab.ACTIVITY -> content.activity
            NotificationTab.COMMUNITY -> content.community
        }

        if (list.isEmpty()) {
            EmptyNotificationComponent()
        } else {
            NotificationListing(
                notifications = list,
                onSelect = { /* Optional if you want to open details */ },
                onDelete = { notification ->
                    onAction(NotificationsAction.Delete(notification))
                }
            )
        }
    }

    @Composable
    private fun DeleteAllAction(onClick: () -> Unit) {
        Box(
            Modifier
                .padding(top = 32.dp, bottom = 54.dp)
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(Res.string.clear_all_action),
                style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.linkInverse)
            )
        }
    }

    @Composable
    private fun SettingsAction(onClick: () -> Unit) {
        Box(Modifier.fillMaxWidth().padding(24.dp)) {
            SkyFitButtonComponent(
                text = stringResource(Res.string.notification_settings_label),
                onClick = onClick,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun NotificationListing(
        notifications: List<SkyFitNotificationDTO>,
        onSelect: (SkyFitNotificationDTO) -> Unit,
        onDelete: (SkyFitNotificationDTO) -> Unit,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(notifications) { notification ->
                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        if (dismissValue == DismissValue.DismissedToStart) {
                            onDelete(notification)
                            true
                        } else {
                            false
                        }
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.5f) },
                    background = { NotificationItemSwipeDismissBackground(dismissState) },
                    dismissContent = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            SkyFitNotificationItem(notification)
                        }
                        Spacer(Modifier.height(12.dp))
                    }
                )
            }
        }
    }

    @Composable
    private fun EmptyNotificationComponent() {
        Box(Modifier.width(400.dp).padding(horizontal = 16.dp, vertical = 36.dp), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(Res.string.empty_notifications_message),
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary),
                textAlign = TextAlign.Center
            )
        }
    }
}