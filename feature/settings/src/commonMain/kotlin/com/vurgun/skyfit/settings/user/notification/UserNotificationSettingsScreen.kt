package com.vurgun.skyfit.settings.user.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.menu.NotificationSettingsItem
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.settings.component.SettingsCompactComponent
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.settings_notifications

class UserNotificationSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserNotificationSettingsViewModel>()

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        if (windowSize == WindowSize.EXPANDED) {
            UserNotificationOptions(viewModel)
        } else {
            UserNotificationSettingsCompact(viewModel = viewModel, goToBack = { navigator.pop() })
        }

    }
}

@Composable
private fun UserNotificationSettingsCompact(
    goToBack: () -> Unit,
    viewModel: UserNotificationSettingsViewModel
) {

    SettingsCompactComponent.Layout(
        topbar = { CompactTopBar(stringResource(Res.string.settings_notifications), onClickBack = goToBack) },
        content = { UserNotificationOptions(viewModel) }
    )
}

@Composable
private fun UserNotificationOptions(
    viewModel: UserNotificationSettingsViewModel
) {
    val options by viewModel.options.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(start = 33.dp, top = 32.dp, end = 23.dp, bottom = 24.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(options) { option ->
            NotificationSettingsItem(
                item = option,
                onToggle = { isEnabled -> viewModel.updateOption(option, isEnabled) }
            )
        }
    }
}
