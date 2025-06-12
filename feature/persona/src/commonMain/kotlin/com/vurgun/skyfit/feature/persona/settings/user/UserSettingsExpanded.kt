package com.vurgun.skyfit.feature.persona.settings.user

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature.persona.settings.component.SettingsExpandedComponent
import com.vurgun.skyfit.feature.persona.settings.component.SettingsExpandedComponent.SideMenuNavigationItem
import com.vurgun.skyfit.feature.persona.settings.model.FacilitySettingsNavigationRoute
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.branches_label
import skyfit.core.ui.generated.resources.ic_building
import skyfit.core.ui.generated.resources.ic_posture_fill
import skyfit.core.ui.generated.resources.member_label
import skyfit.core.ui.generated.resources.members_label

@Composable
fun UserSettingsExpanded(viewModel: UserSettingViewModel) {



    navigationItems.forEach { item ->
        when(item) {
            FacilitySettingsNavigationRoute.Branches -> {
                SideMenuNavigationItem(
                    text = stringResource(Res.string.branches_label),
                    iconRes = Res.drawable.ic_building,
                    onClick = { viewModel.onAction() }
                )
            }

            FacilitySettingsNavigationRoute.Members -> {
                SideMenuNavigationItem(
                    text = stringResource(Res.string.members_label),
                    iconRes = Res.drawable.ic_posture_fill,
                    onClick = TODO()
                )
            }
            FacilitySettingsNavigationRoute.Packages -> TODO()
            FacilitySettingsNavigationRoute.Trainers -> TODO()
            SettingsNavigationRoute.Account -> TODO()
            SettingsNavigationRoute.Notifications -> TODO()
            SettingsNavigationRoute.PaymentHistory -> TODO()
            SettingsNavigationRoute.Support -> TODO()
        }
    }

    SettingsExpandedComponent.SideNavigationMenu(
        navigationItems = listOf(
            SettingsNavigationRoute.Account,
            SettingsNavigationRoute.PaymentHistory,
            SettingsNavigationRoute.Notifications,
            SettingsNavigationRoute.Support,
        ),
        onClickLogout = {

        }
    )
}