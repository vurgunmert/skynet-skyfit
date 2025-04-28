package com.vurgun.skyfit.feature.notification.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.model.SkyFitNotification
import com.vurgun.skyfit.feature.notification.UserNotificationsViewModel
import com.vurgun.skyfit.feature.notification.component.NotificationItemSwipeDismissBackground
import com.vurgun.skyfit.feature.notification.component.SkyFitNotificationItem
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitBadgeTabBarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.koin.compose.koinInject

@Composable
fun MobileUserNotificationsScreen(
    goBack: () -> Unit,
    goToSettings: () -> Unit
) {

    val viewModel: UserNotificationsViewModel = koinInject()

    val allNotifications by viewModel.allNotifications.collectAsState()
    val activityNotifications by viewModel.activityNotifications.collectAsState()
    val communityNotifications by viewModel.communityNotifications.collectAsState()

    var activeTab by remember { mutableStateOf(0) }
    val tabTitles by viewModel.tabTitles.collectAsState()

    val onSelectNotification: (SkyFitNotification) -> Unit = { notification ->

    }

    val onDeleteNotification: (SkyFitNotification) -> Unit = { notification ->
        viewModel.deleteNotification(notification)
    }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

   SkyFitMobileScaffold(
        topBar = {
            Column {
                SkyFitScreenHeader("Bildirimler", onClickBack = goBack)
                SkyFitBadgeTabBarComponent(tabTitles, activeTab, onTabSelected = { activeTab = it })
            }
        },
        bottomBar = {
            if (allNotifications.isEmpty()) {
                MobileUserNotificationsScreenSettingsActionComponent(onClick = goToSettings)
            } else {
                MobileUserNotificationsScreenDeleteAllActionComponent(onClick = viewModel::deleteAllNotifications)
            }
        }
    ) {
        if (allNotifications.isEmpty()) {
            EmptyNotificationsComponent()
        } else {
            when (activeTab) {
                0 -> NotificationListComponent(allNotifications, onSelectNotification, onDeleteNotification)
                1 -> NotificationListComponent(activityNotifications, onSelectNotification, onDeleteNotification)
                2 -> NotificationListComponent(communityNotifications, onSelectNotification, onDeleteNotification)
            }
        }
    }
}

@Composable
private fun MobileUserNotificationsScreenDeleteAllActionComponent(onClick: () -> Unit) {
    Box(
        Modifier
            .padding(top = 32.dp, bottom = 54.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "Hepsini temizle",
            style = SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.linkInverse)
        )
    }
}

@Composable
private fun MobileUserNotificationsScreenSettingsActionComponent(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(24.dp)) {
        SkyFitButtonComponent(
            text = "Bilidirim Ayarları",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NotificationListComponent(
    notifications: List<SkyFitNotification>,
    onSelect: (SkyFitNotification) -> Unit,
    onDelete: (SkyFitNotification) -> Unit,
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
private fun EmptyNotificationsComponent() {
    Box(Modifier.width(400.dp).padding(horizontal = 16.dp, vertical = 36.dp), contentAlignment = Alignment.Center) {
        Text(
            text = "Şu anda size iletilmiş bir bildirim bulunmuyor. Ders hatırlatmaları, etkinlik güncellemeleri ve özel tekliflerden haberdar olmak için ayarlarınızı kontrol edebilirsiniz.",
            style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary),
            textAlign = TextAlign.Center
        )
    }
}