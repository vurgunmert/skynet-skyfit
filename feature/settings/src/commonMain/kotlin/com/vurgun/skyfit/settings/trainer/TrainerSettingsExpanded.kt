package com.vurgun.skyfit.settings.trainer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.feature.persona.settings.trainer.TrainerAccountSettingsScreen
import com.vurgun.skyfit.settings.component.SettingsExpandedComponent
import com.vurgun.skyfit.settings.shared.SettingsUiAction
import com.vurgun.skyfit.settings.shared.SettingsViewModel

@Composable
fun TrainerSettingsExpanded(viewModel: SettingsViewModel) {

//    val settingsRoutes = remember { viewModel.getTrainerRoutes() }
//
//    Navigator(TrainerAccountSettingsScreen()) { settingsNavigator ->
//
//        Row(Modifier.fillMaxSize()) {
//            SettingsExpandedComponent.SideNavigationMenu(
//                modifier = Modifier.width(315.dp),
//                activeScreen = settingsNavigator.lastItem,
//                routes = settingsRoutes,
//                onClickRoute = { viewModel.onAction(SettingsUiAction.OnDestinationChanged(it)) },
//                onClickLogout = { viewModel.onAction(SettingsUiAction.OnClickLogout) }
//            )
//            Spacer(Modifier.width(16.dp))
//
//            CrossfadeTransition(settingsNavigator)
//        }
//    }
}