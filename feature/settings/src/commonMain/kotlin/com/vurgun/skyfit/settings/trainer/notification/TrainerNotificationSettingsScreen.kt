package com.vurgun.skyfit.settings.trainer.notification

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
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.notifications_label

class TrainerNotificationSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerNotificationSettingsViewModel>()

        MobileTrainerSettingsNotificationsScreen(
            goToBack = { navigator.pop() },
            viewModel = viewModel
        )
    }

}

@Composable
private fun MobileTrainerSettingsNotificationsScreen(
    goToBack: () -> Unit,
    viewModel: TrainerNotificationSettingsViewModel
) {
    val options by viewModel.options.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar(stringResource(Res.string.notifications_label), onClickBack = goToBack)
        }
    ) {
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
}
