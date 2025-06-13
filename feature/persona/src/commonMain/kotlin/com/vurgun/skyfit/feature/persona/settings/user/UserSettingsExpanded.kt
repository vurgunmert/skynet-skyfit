package com.vurgun.skyfit.feature.persona.settings.user

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.component.SettingsExpandedComponent
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiEffect
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.feature.persona.settings.user.notification.UserNotificationSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.user.payment.UserSettingsPaymentHistoryScreen

@Composable
fun UserSettingsExpanded(viewModel: SettingsViewModel) {
    val rootNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val settingsRoutes = remember { viewModel.getUserRoutes() }

    val startScreen = remember { UserAccountSettingsScreen() }

    Navigator(startScreen) { settingsNavigator ->

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                SettingsUiEffect.NavigateToBack -> TODO()
                is SettingsUiEffect.NavigateToRoute -> {
                    when (effect.route) {
                        SettingsNavigationRoute.Account ->
                            settingsNavigator.replace(UserAccountSettingsScreen())

                        SettingsNavigationRoute.PaymentHistory ->
                            settingsNavigator.replace(UserSettingsPaymentHistoryScreen())

                        SettingsNavigationRoute.Notifications ->
                            settingsNavigator.replace(UserNotificationSettingsScreen())

                        SettingsNavigationRoute.Support ->
                            settingsNavigator.replace(SettingsSupportHelpScreen())

                        else -> Unit
                    }
                }

                SettingsUiEffect.NavigateToSplash ->
                    rootNavigator.replaceAll(SharedScreen.Splash)
            }
        }

        Row(Modifier.fillMaxSize()) {
            SettingsExpandedComponent.SideNavigationMenu(
                modifier = Modifier.width(315.dp).fillMaxHeight(),
                routes = settingsRoutes,
                activeScreen = settingsNavigator.lastItem,
                onClickRoute = { viewModel.onAction(SettingsUiAction.OnChangeRoute(it)) },
                onClickLogout = { viewModel.onAction(SettingsUiAction.OnClickLogout) }
            )

            Spacer(Modifier.width(16.dp))

            CrossfadeTransition(settingsNavigator)
        }
    }
}
