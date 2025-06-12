package com.vurgun.skyfit.feature.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_chat

@Composable
internal fun DefaultCompactTopBar(
    state: HomeTopBarState = HomeTopBarState(),
    onNavigate: (ScreenProvider) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.weight(1f))

        HomeCompactTopBarActionRow(
            state.notificationsEnabled,
            { onNavigate(SharedScreen.Notifications) },
            state.conversationsEnabled,
            { onNavigate(SharedScreen.Conversations) },
        )
    }
}

@Composable
internal fun HomeCompactTopBarActionRow(
    notificationsEnabled: Boolean,
    onClickNotifications: () -> Unit,
    conversationsEnabled: Boolean,
    onClickConversations: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FeatureVisible(notificationsEnabled) {
            Spacer(Modifier.width(16.dp))
            SkyIcon(
                res = Res.drawable.ic_bell,
                size = SkyIconSize.Normal,
                onClick = onClickNotifications
            )
        }

        FeatureVisible(conversationsEnabled) {
            Spacer(Modifier.width(16.dp))
            SkyIcon(
                res = Res.drawable.ic_chat,
                size = SkyIconSize.Normal,
                onClick = onClickConversations
            )
        }
    }
}