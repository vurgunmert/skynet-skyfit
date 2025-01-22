package com.vurgun.skyfit.presentation.mobile.features.user.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserNotificationsScreen(navigator: Navigator) {

    var notifications: List<Any> = listOf(1,2)

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileUserNotificationsScreenToolbarComponent()
                MobileUserNotificationsScreenTabsComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (notifications.isEmpty()) {
                MobileUserNotificationsComponent()
                Spacer(Modifier.height(32.dp))
                MobileUserNotificationsScreenDeleteAllActionComponent()
                Spacer(Modifier.height(64.dp))
            } else {
                MobileUserNotificationsEmptyComponent()
                Spacer(Modifier.weight(1f))
                MobileUserNotificationsScreenSettingsActionComponent()
            }
        }
    }
}

@Composable
private fun MobileUserNotificationsScreenToolbarComponent() {
    TodoBox("MobileUserNotificationsScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserNotificationsScreenTabsComponent() {
    TodoBox("MobileUserNotificationsScreenTabsComponent", Modifier.size(430.dp, 52.dp))
}

@Composable
private fun MobileUserNotificationsComponent() {
    TodoBox("MobileUserNotificationsComponent", Modifier.size(430.dp, 640.dp))
}

@Composable
private fun MobileUserNotificationsScreenDeleteAllActionComponent() {
    TodoBox("MobileUserNotificationsScreenDeleteAllActionComponent", Modifier.size(430.dp, 36.dp))
}

@Composable
private fun MobileUserNotificationsEmptyComponent() {
    TodoBox("MobileUserNotificationsEmptyComponent", Modifier.size(430.dp, 144.dp))
}

@Composable
private fun MobileUserNotificationsScreenSettingsActionComponent() {
    TodoBox("MobileUserNotificationsScreenSettingsActionComponent", Modifier.size(430.dp, 48.dp))
}